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

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;

import com.gargoylesoftware.htmlunit.html.HtmlTime;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxSetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * The JavaScript object {@code HTMLTimeElement}.
 *
 * @author Ronald Brill
 * @author Ahmed Ashour
 */
@JsxClass(domClass = HtmlTime.class, browsers = @WebBrowser(FF))
public class HTMLTimeElement extends HTMLElement {

    /**
     * Creates an instance.
     */
    @JsxConstructor
    public HTMLTimeElement() {
    }

    /**
     * Returns the dateTime.
     * @return the dateTime
     */
    @JsxGetter
    public String getDateTime() {
        return getDomNodeOrDie().getAttribute("dateTime");
    }

    /**
     * Sets the dateTime.
     * @param dateTime the dateTime
     */
    @JsxSetter
    public void setDateTime(final String dateTime) {
        getDomNodeOrDie().setAttribute("dateTime", dateTime);
    }
}
