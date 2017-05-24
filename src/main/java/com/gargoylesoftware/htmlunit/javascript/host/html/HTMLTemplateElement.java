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
package com.gargoylesoftware.htmlunit.javascript.host.html;

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlTemplate;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import com.gargoylesoftware.htmlunit.javascript.host.dom.DocumentFragment;

import net.sourceforge.htmlunit.corejs.javascript.Context;

/**
 * The JavaScript object {@code HTMLTemplateElement}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@JsxClass(domClass = HtmlTemplate.class, browsers = {@WebBrowser(CHROME), @WebBrowser(FF)})
public class HTMLTemplateElement extends HTMLElement {

    /**
     * Creates a new instance.
     */
    @JsxConstructor
    public HTMLTemplateElement() {
    }

    /**
     * @return the value of the {@code content} property
     */
    @JsxGetter
    public DocumentFragment getContent() {
        final DocumentFragment result = new DocumentFragment();
        result.setPrototype(getPrototype(result.getClass()));
        result.setParentScope(getParentScope());
        result.setDomNode(((HtmlTemplate) getDomNodeOrDie()).getContent());

        return result;
    }

    /**
     * Gets the innerHTML attribute.
     * @return the contents of this node as HTML
     */
    @JsxGetter
    @Override
    public String getInnerHTML() {
        final DomNode domNode;
        try {
            domNode = ((HtmlTemplate) getDomNodeOrDie()).getContent();
        }
        catch (final IllegalStateException e) {
            Context.throwAsScriptRuntimeEx(e);
            return "";
        }
        return getInnerHTML(domNode);
    }
}
