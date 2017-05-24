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
package com.gargoylesoftware.htmlunit.javascript;

import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.CHROME;
import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.FF;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;

/**
 * Object is a native JavaScript object and therefore provided by Rhino but some tests are needed here
 * to be sure that we have the expected results.
 *
 * @author Marc Guillemot
 * @author Frank Danek
 * @author Ahmed Ashour
 */
@RunWith(BrowserRunner.class)
public class NativeObjectTest extends WebDriverTestCase {

    /**
     * Test for the methods with the same expectations for all browsers.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"assign: undefined", "constructor: function", "create: undefined", "defineProperties: undefined",
                "defineProperty: undefined", "freeze: undefined", "getOwnPropertyDescriptor: undefined",
                "getOwnPropertyNames: undefined", "getPrototypeOf: undefined", "hasOwnProperty: function",
                "isExtensible: undefined", "isFrozen: undefined", "isPrototypeOf: function", "isSealed: undefined",
                "keys: undefined", "preventExtensions: undefined", "propertyIsEnumerable: function", "seal: undefined",
                "toLocaleString: function", "toString: function", "valueOf: function", "__defineGetter__: function",
                "__defineSetter__: function", "__lookupGetter__: function", "__lookupSetter__: function"},
            FF52 = {"assign: undefined", "constructor: function", "create: undefined", "defineProperties: undefined",
                "defineProperty: undefined", "freeze: undefined", "getOwnPropertyDescriptor: undefined",
                "getOwnPropertyNames: undefined", "getPrototypeOf: undefined", "hasOwnProperty: function",
                "isExtensible: undefined", "isFrozen: undefined", "isPrototypeOf: function", "isSealed: undefined",
                "keys: undefined", "preventExtensions: undefined", "propertyIsEnumerable: function", "seal: undefined",
                "toLocaleString: function", "toString: function", "valueOf: function", "__defineGetter__: function"})
    public void common() throws Exception {
        final String[] methods = {"assign", "constructor", "create", "defineProperties", "defineProperty", "freeze",
            "getOwnPropertyDescriptor", "getOwnPropertyNames", "getPrototypeOf", "hasOwnProperty", "isExtensible",
            "isFrozen", "isPrototypeOf", "isSealed", "keys", "preventExtensions", "propertyIsEnumerable", "seal",
            "toLocaleString", "toString", "valueOf", "__defineGetter__", "__defineSetter__",
            "__lookupGetter__", "__lookupSetter__"};
        final String html = NativeDateTest.createHTMLTestMethods("new Object()", methods);
        loadPageWithAlerts2(html, 2 * DEFAULT_WAIT_TIME);
    }

    /**
     * Test for the methods with the different expectations depending on the browsers.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "toSource: undefined",
            FF = "toSource: function")
    public void others() throws Exception {
        final String[] methods = {"toSource"};
        final String html = NativeDateTest.createHTMLTestMethods("new Object()", methods);
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = {})
    public void assign() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function test() {\n"
            + "  if (Object.assign) {\n"
            + "    var obj = { a: 1 };\n"
            + "    var copy = Object.assign({}, obj);\n"
            + "    alert(copy.a);\n"
            + "  }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = {})
    public void assignUndefined() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function test() {\n"
            + "  if (Object.assign) {\n"
            + "    var obj = { a: 1 };\n"
            + "    var copy = Object.assign({}, undefined, obj);\n"
            + "    alert(copy.a);\n"
            + "  }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "function () {}",
            CHROME = "function () { [native code] }",
            FF = "function () {\n}",
            IE = "\nfunction() {\n    [native code]\n}\n")
    @NotYetImplemented
    public void proto() throws Exception {
        final String html = ""
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    alert(Object.__proto__);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test case for #1856.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object Object]", "null"})
    public void proto2() throws Exception {
        final String html = ""
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    alert({}.__proto__);\n"
            + "    alert({}.__proto__.__proto__);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test case for #1855.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "",
            IE = "exception")
    public void getPrototypeOf() throws Exception {
        final String html = ""
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      alert(Object.getPrototypeOf(''));\n"
            + "    } catch(e) {alert('exception')}\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            IE = "exception")
    public void getPrototypeOfNumber() throws Exception {
        final String html = ""
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      alert(Object.getPrototypeOf(1));\n"
            + "    } catch(e) {alert('exception')}\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "object",
            IE = "exception")
    @NotYetImplemented({CHROME, FF})
    public void getTypeOfPrototypeOfNumber() throws Exception {
        final String html = ""
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      alert(typeof Object.getPrototypeOf(1));\n"
            + "    } catch(e) {alert('exception')}\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }
}
