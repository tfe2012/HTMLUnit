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
package com.gargoylesoftware.htmlunit.javascript.host;

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.EDGE;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;

import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * A JavaScript object for {@code MimeTypeArray}.
 *
 * @author Marc Guillemot
 * @author Ahmed Ashour
 *
 * @see <a href="http://www.xulplanet.com/references/objref/MimeTypeArray.html">XUL Planet</a>
 */
@JsxClass
public class MimeTypeArray extends SimpleArray {

    /**
     * Creates an instance.
     */
    @JsxConstructor({@WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE)})
    public MimeTypeArray() {
    }

    /**
     * Gets the name of the mime type.
     * @param element a {@link MimeType}
     * @return the name
     */
    @Override
    protected String getItemName(final Object element) {
        return ((MimeType) element).getType();
    }
}
