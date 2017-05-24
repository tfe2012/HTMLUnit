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
package com.gargoylesoftware.htmlunit.javascript.host.canvas.ext;

import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.IE;

import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstant;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * A JavaScript object for {@code WEBGL_debug_renderer_info}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@JsxClass(browsers = @WebBrowser(IE))
public class WEBGL_debug_renderer_info extends SimpleScriptable {

    /** The constant {@code UNMASKED_RENDERER_WEBGL}. */
    @JsxConstant
    public static final int UNMASKED_RENDERER_WEBGL = 37446;
    /** The constant {@code UNMASKED_VENDOR_WEBGL}. */
    @JsxConstant
    public static final int UNMASKED_VENDOR_WEBGL = 37445;

    /**
     * Default constructor.
     */
    public WEBGL_debug_renderer_info() {
    }
}
