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
package com.gargoylesoftware.htmlunit.svg;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.ScriptElement;
import com.gargoylesoftware.htmlunit.html.ScriptElementSupport;
import com.gargoylesoftware.htmlunit.util.EncodingSniffer;

/**
 * Wrapper for the SVG element {@code script}.
 *
 * @author Ahmed Ashour
 */
public class SvgScript extends SvgElement implements ScriptElement {

    /** The tag represented by this element. */
    public static final String TAG_NAME = "script";
    private boolean executed_;

    /**
     * Creates a new instance.
     *
     * @param namespaceURI the URI that identifies an XML namespace
     * @param qualifiedName the qualified name of the element type to instantiate
     * @param page the page that contains this element
     * @param attributes the initial attributes
     */
    SvgScript(final String namespaceURI, final String qualifiedName, final SgmlPage page,
            final Map<String, DomAttr> attributes) {
        super(namespaceURI, qualifiedName, page, attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExecuted() {
        return executed_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecuted(final boolean executed) {
        executed_ = executed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getSrcAttribute() {
        return getSrcAttributeNormalized();
    }

    /**
     * Helper for src retrieval and normalization.
     *
     * @return the value of the attribute {@code src} with all line breaks removed
     * or an empty string if that attribute isn't defined.
     */
    protected final String getSrcAttributeNormalized() {
        // at the moment StringUtils.replaceChars returns the org string
        // if nothing to replace was found but the doc implies, that we
        // can't trust on this in the future
        final String attrib = getAttribute("src");
        if (ATTRIBUTE_NOT_DEFINED == attrib) {
            return attrib;
        }

        return StringUtils.replaceChars(attrib, "\r\n", "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getCharsetAttribute() {
        return getAttribute("charset");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Charset getCharset() {
        return EncodingSniffer.toCharset(getCharsetAttribute());
    }

    /**
     * Executes the <tt>onreadystatechange</tt> handler when simulating IE, as well as executing
     * the script itself, if necessary. {@inheritDoc}
     */
    @Override
    protected void onAllChildrenAddedToPage(final boolean postponed) {
        ScriptElementSupport.onAllChildrenAddedToPage(this, postponed);
    }
}
