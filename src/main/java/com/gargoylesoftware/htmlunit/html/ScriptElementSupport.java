/*
 * Copyright (c) 2002-2017 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.html;

import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.EVENT_ONLOAD_INTERNAL_JAVASCRIPT;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.HTMLSCRIPT_TRIM_TYPE;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_SCRIPT_SUPPORTS_FOR_AND_EVENT_WINDOW;
import static com.gargoylesoftware.htmlunit.html.DomElement.ATTRIBUTE_NOT_DEFINED;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPage.JavaScriptLoadResult;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;
import com.gargoylesoftware.htmlunit.javascript.host.event.EventHandler;
import com.gargoylesoftware.htmlunit.javascript.host.event.EventTarget;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
import com.gargoylesoftware.htmlunit.protocol.javascript.JavaScriptURLConnection;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;

/**
 * A helper class to be used by elements which support {@link ScriptElement}.
 *
 * @author Ahmed Ashour
 */
public final class ScriptElementSupport {

    private static final Log LOG = LogFactory.getLog(ScriptElementSupport.class);
    /** Invalid source attribute which should be ignored (used by JS libraries like jQuery). */
    private static final String SLASH_SLASH_COLON = "//:";

    private ScriptElementSupport() {
    }

    /**
     * Lifecycle method invoked after a node and all its children have been added to a page, during
     * parsing of the HTML. Intended to be overridden by nodes which need to perform custom logic
     * after they and all their child nodes have been processed by the HTML parser. This method is
     * not recursive, and the default implementation is empty, so there is no need to call
     * <tt>super.onAllChildrenAddedToPage()</tt> if you implement this method.
     * @param element the element
     * @param postponed whether to use {@link com.gargoylesoftware.htmlunit.javascript.PostponedAction} or no
     */
    public static void onAllChildrenAddedToPage(final DomElement element, final boolean postponed) {
        if (element.getOwnerDocument() instanceof XmlPage) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Script node added: " + element.asXml());
        }

        final PostponedAction action = new PostponedAction(element.getPage(), "Execution of script " + element) {
            @Override
            public void execute() {
                final HTMLDocument jsDoc = (HTMLDocument)
                        ((Window) element.getPage().getEnclosingWindow().getScriptableObject()).getDocument();
                jsDoc.setExecutingDynamicExternalPosponed(element.getStartLineNumber() == -1
                        && ((ScriptElement) element).getSrcAttribute() != ATTRIBUTE_NOT_DEFINED);

                try {
                    executeScriptIfNeeded(element);
                }
                finally {
                    jsDoc.setExecutingDynamicExternalPosponed(false);
                }
            }
        };

        final JavaScriptEngine engine = element.getPage().getWebClient().getJavaScriptEngine();
        if (element.hasAttribute("async") && !engine.isScriptRunning()) {
            final HtmlPage owningPage = element.getHtmlPageOrNull();
            owningPage.addAfterLoadAction(action);
        }
        else if (element.hasAttribute("async")
                || postponed && StringUtils.isBlank(element.getTextContent())) {
            engine.addPostponedAction(action);
        }
        else {
            try {
                action.execute();
            }
            catch (final RuntimeException e) {
                throw e;
            }
            catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * <span style="color:red">INTERNAL API - SUBJECT TO CHANGE AT ANY TIME - USE AT YOUR OWN RISK.</span><br>
     *
     * Executes this script node if necessary and/or possible.
     * @param element the element
     */
    public static void executeScriptIfNeeded(final DomElement element) {
        if (!isExecutionNeeded(element)) {
            return;
        }

        final HtmlPage page = (HtmlPage) element.getPage();

        final String src = ((ScriptElement) element).getSrcAttribute();
        if (src.equals(SLASH_SLASH_COLON)) {
            executeEvent(element, Event.TYPE_ERROR);
            return;
        }

        if (src != ATTRIBUTE_NOT_DEFINED) {
            if (!src.startsWith(JavaScriptURLConnection.JAVASCRIPT_PREFIX)) {
                // <script src="[url]"></script>
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Loading external JavaScript: " + src);
                }
                try {
                    final ScriptElement scriptElement = (ScriptElement) element;
                    scriptElement.setExecuted(true);
                    final JavaScriptLoadResult result =
                            page.loadExternalJavaScriptFile(src, scriptElement.getCharset());
                    if (result == JavaScriptLoadResult.SUCCESS) {
                        executeEvent(element, Event.TYPE_LOAD);
                    }
                    else if (result == JavaScriptLoadResult.DOWNLOAD_ERROR) {
                        executeEvent(element, Event.TYPE_ERROR);
                    }
                }
                catch (final FailingHttpStatusCodeException e) {
                    executeEvent(element, Event.TYPE_ERROR);
                    throw e;
                }
            }
        }
        else if (element.getFirstChild() != null) {
            // <script>[code]</script>
            executeInlineScriptIfNeeded(element);

            if (element.hasFeature(EVENT_ONLOAD_INTERNAL_JAVASCRIPT)) {
                executeEvent(element, Event.TYPE_LOAD);
            }
        }
    }

    /**
     * Indicates if script execution is necessary and/or possible.
     *
     * @param element the element
     * @return {@code true} if the script should be executed
     */
    private static boolean isExecutionNeeded(final DomElement element) {
        if (((ScriptElement) element).isExecuted()) {
            return false;
        }

        if (!element.isAttachedToPage()) {
            return false;
        }

        // If JavaScript is disabled, we don't need to execute.
        final SgmlPage page = element.getPage();
        if (!page.getWebClient().getOptions().isJavaScriptEnabled()) {
            return false;
        }

        // If innerHTML or outerHTML is being parsed
        final HtmlPage htmlPage = element.getHtmlPageOrNull();
        if (htmlPage != null && htmlPage.isParsingHtmlSnippet()) {
            return false;
        }

        // If the script node is nested in an iframe, a noframes, or a noscript node, we don't need to execute.
        for (DomNode o = element; o != null; o = o.getParentNode()) {
            if (o instanceof HtmlInlineFrame || o instanceof HtmlNoFrames) {
                return false;
            }
        }

        // If the underlying page no longer owns its window, the client has moved on (possibly
        // because another script set window.location.href), and we don't need to execute.
        if (page.getEnclosingWindow() != null && page.getEnclosingWindow().getEnclosedPage() != page) {
            return false;
        }

        // If the script language is not JavaScript, we can't execute.
        final String t = element.getAttribute("type");
        final String l = element.getAttribute("language");
        if (!isJavaScript(element, t, l)) {
            LOG.warn("Script is not JavaScript (type: " + t + ", language: " + l + "). Skipping execution.");
            return false;
        }

        // If the script's root ancestor node is not the page, then the script is not a part of the page.
        // If it isn't yet part of the page, don't execute the script; it's probably just being cloned.

        return element.getPage().isAncestorOf(element);
    }

    /**
     * Returns true if a script with the specified type and language attributes is actually JavaScript.
     * According to <a href="http://www.w3.org/TR/REC-html40/types.html#h-6.7">W3C recommendation</a>
     * are content types case insensitive.<b>
     * IE supports only a limited number of values for the type attribute. For testing you can
     * use http://www.robinlionheart.com/stds/html4/scripts.
     * @param element the element
     * @param typeAttribute the type attribute specified in the script tag
     * @param languageAttribute the language attribute specified in the script tag
     * @return true if the script is JavaScript
     */
    static boolean isJavaScript(final DomElement element, String typeAttribute, final String languageAttribute) {
        final BrowserVersion browserVersion = element.getPage().getWebClient().getBrowserVersion();

        if (browserVersion.hasFeature(HTMLSCRIPT_TRIM_TYPE)) {
            typeAttribute = typeAttribute.trim();
        }

        if (StringUtils.isNotEmpty(typeAttribute)) {
            if ("text/javascript".equalsIgnoreCase(typeAttribute)
                    || "text/ecmascript".equalsIgnoreCase(typeAttribute)) {
                return true;
            }

            if ("application/javascript".equalsIgnoreCase(typeAttribute)
                            || "application/ecmascript".equalsIgnoreCase(typeAttribute)
                            || "application/x-javascript".equalsIgnoreCase(typeAttribute)) {
                return true;
            }
            return false;
        }

        if (StringUtils.isNotEmpty(languageAttribute)) {
            return StringUtils.startsWithIgnoreCase(languageAttribute, "javascript");
        }
        return true;
    }

    private static void executeEvent(final DomElement element, final String type) {
        final EventTarget eventTarget = (EventTarget) element.getScriptableObject();
        final Event event = new Event(element, type);
        eventTarget.executeEventLocally(event);
    }

    /**
     * Executes this script node as inline script if necessary and/or possible.
     */
    private static void executeInlineScriptIfNeeded(final DomElement element) {
        if (!isExecutionNeeded(element)) {
            return;
        }

        final String src = ((ScriptElement) element).getSrcAttribute();
        if (src != ATTRIBUTE_NOT_DEFINED) {
            return;
        }

        final String forr = element.getAttribute("for");
        String event = element.getAttribute("event");
        // The event name can be like "onload" or "onload()".
        if (event.endsWith("()")) {
            event = event.substring(0, event.length() - 2);
        }

        final String scriptCode = getScriptCode(element);
        if (event != ATTRIBUTE_NOT_DEFINED && forr != ATTRIBUTE_NOT_DEFINED) {
            if (element.hasFeature(JS_SCRIPT_SUPPORTS_FOR_AND_EVENT_WINDOW) && "window".equals(forr)) {
                final Window window = (Window) element.getPage().getEnclosingWindow().getScriptableObject();
                final BaseFunction function = new EventHandler(element, event, scriptCode);
                window.getEventListenersContainer().addEventListener(StringUtils.substring(event, 2), function, false);
                return;
            }
        }
        if (forr == ATTRIBUTE_NOT_DEFINED || "onload".equals(event)) {
            final String url = element.getPage().getUrl().toExternalForm();
            final int line1 = element.getStartLineNumber();
            final int line2 = element.getEndLineNumber();
            final int col1 = element.getStartColumnNumber();
            final int col2 = element.getEndColumnNumber();
            final String desc = "script in " + url + " from (" + line1 + ", " + col1
                + ") to (" + line2 + ", " + col2 + ")";

            ((ScriptElement) element).setExecuted(true);
            ((HtmlPage) element.getPage()).executeJavaScriptIfPossible(scriptCode, desc, line1);
        }
    }

    /**
     * Gets the script held within the script tag.
     */
    private static String getScriptCode(final DomElement element) {
        final Iterable<DomNode> textNodes = element.getChildren();
        final StringBuilder scriptCode = new StringBuilder();
        for (final DomNode node : textNodes) {
            if (node instanceof DomText) {
                final DomText domText = (DomText) node;
                scriptCode.append(domText.getData());
            }
        }
        return scriptCode.toString();
    }

}
