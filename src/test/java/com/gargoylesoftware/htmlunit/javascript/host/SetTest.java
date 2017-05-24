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

import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.CHROME;
import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.FF;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;

/**
 * Tests for {@link Set}.
 *
 * @author Ahmed Ashour
 */
@RunWith(BrowserRunner.class)
public class SetTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"3", "true"},
            IE = {"1", "false"})
    public void has() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var myArray = ['value1', 'value2', 'value3'];\n"
            + "    var mySet = new Set(myArray);\n"
            + "    mySet.add('value1');\n"
            + "    alert(mySet.size);\n"
            + "    alert(mySet.has('value2'));\n"
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
    @Alerts(DEFAULT = {"function values() { [native code] }",
                "[object Set Iterator]", "0", "1", "[object Object]"},
            FF = {"function values() {\n    [native code]\n}",
                    "[object Set Iterator]", "0", "1", "[object Object]"},
            IE = {})
    public void iterator() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.Symbol) {\n"
            + "      var set = new Set();\n"
            + "      set.add('0');\n"
            + "      set.add(1);\n"
            + "      set.add({});\n"
            + "      alert(set[Symbol.iterator]);\n"
            + "      var iter = set[Symbol.iterator]();\n"
            + "      alert(iter);\n"
            + "      alert(iter.next().value);\n"
            + "      alert(iter.next().value);\n"
            + "      alert(iter.next().value);\n"
            + "    }\n"
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
    @Alerts(DEFAULT = {"function values() { [native code] }",
                "[object Set Iterator]", "0", "1", "[object Object]"},
            FF = {"function values() {\n    [native code]\n}",
                "[object Set Iterator]", "0", "1", "[object Object]"},
            IE = {})
    public void values() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.Symbol) {\n"
            + "      var set = new Set();\n"
            + "      set.add('0');\n"
            + "      set.add(1);\n"
            + "      set.add({});\n"
            + "      alert(set.values);\n"
            + "      var iter = set.values();\n"
            + "      alert(iter);\n"
            + "      alert(iter.next().value);\n"
            + "      alert(iter.next().value);\n"
            + "      alert(iter.next().value);\n"
            + "    }\n"
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
    @Alerts(DEFAULT = "2",
            IE = "0")
    public void constructor() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var array = new Int32Array([2, 7]);\n"
            + "  var mySet = new Set(array);\n"
            + "  alert(mySet.size);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"3", "true", "false"},
            IE = {"0", "false", "false"})
    public void constructorStringParam() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var mySet = new Set('test');\n"
            + "  alert(mySet.size);\n"
            + "  alert(mySet.has('t'));\n"
            + "  alert(mySet.has('x'));\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"3", "true", "false"},
            IE = {"0", "false", "false"})
    public void constructorSetParam() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var mySet = new Set(new Set('test'));\n"
            + "  alert(mySet.size);\n"
            + "  alert(mySet.has('t'));\n"
            + "  alert(mySet.has('x'));\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"2", "false", "false"},
            IE = {"0", "false", "false"})
    public void constructorMapParam() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var kvArray = [['key1', 'value1'], ['key2', 'value2']];\n"
            + "  var myMap = new Map(kvArray);\n"
            + "  var mySet = new Set(myMap);\n"
            + "  alert(mySet.size);\n"
            + "  alert(mySet.has('key1'));\n"
            + "  alert(mySet.has('value2'));\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"ab", "ab", "[object Set]", "[object Window]",
            "undefined", "undefined", "[object Set]", "[object Window]",
            "null", "null", "[object Set]", "[object Window]"},
            IE = {})
    public void forEach() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function logElement(value1, value2, s) {\n"
            + "  alert(value1);\n"
            + "  alert(value2);\n"
            + "  alert(s);\n"
            + "  alert(this);\n"
            + "}\n"
            + "function test() {\n"
            + "  var mySet = new Set(['ab', undefined, null]);\n"
            + "  mySet.forEach(logElement);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"ab", "ab", "[object Set]", "hello", "undefined", "undefined", "[object Set]", "hello",
            "null", "null", "[object Set]", "hello"},
            IE = {})
    @NotYetImplemented({CHROME, FF})
    public void forEachThis() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function logElement(value1, value2, s) {\n"
            + "  alert(value1);\n"
            + "  alert(value2);\n"
            + "  alert(s);\n"
            + "  alert(this);\n"
            + "}\n"
            + "function test() {\n"
            + "  var mySet = new Set(['ab', undefined, null]);\n"
            + "  mySet.forEach(logElement, 'hello');\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

}
