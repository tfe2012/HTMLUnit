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
 * Tests for {@link Map}.
 *
 * @author Ahmed Ashour
 */
@RunWith(BrowserRunner.class)
public class MapTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"3", "value1"},
            IE = {"1", "undefined"})
    public void get() throws Exception {
        final String html = "<html><head><script>\n"
            + "  function test() {\n"
            + "    if (window.Map) {\n"
            + "      var kvArray = [['key1', 'value1'], ['key2', 'value2']];\n"
            + "      var myMap = new Map(kvArray);\n"
            + "      myMap.set(1, 2);\n"
            + "      alert(myMap.size);\n"
            + "      alert(myMap.get('key1'));\n"
            + "    }\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"function entries() { [native code] }",
                "[object Map Iterator]", "0,foo", "1,bar", "[object Object],baz", "undefined"},
            FF = {"function entries() {\n    [native code]\n}",
                "[object Map Iterator]", "0,foo", "1,bar", "[object Object],baz", "undefined"},
            IE = {})
    public void iterator() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.Symbol) {\n"
            + "      var myMap = new Map();\n"
            + "      myMap.set('0', 'foo');\n"
            + "      myMap.set(1, 'bar');\n"
            + "      myMap.set({}, 'baz');\n"
            + "      alert(myMap[Symbol.iterator]);\n"
            + "      var iter = myMap[Symbol.iterator]();\n"
            + "      alert(iter);\n"
            + "      alert(iter.next().value);\n"
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
    @Alerts(DEFAULT = {"function entries() { [native code] }",
                "[object Map Iterator]", "0,foo", "1,bar", "[object Object],baz", "undefined"},
            FF = {"function entries() {\n    [native code]\n}",
                "[object Map Iterator]", "0,foo", "1,bar", "[object Object],baz", "undefined"},
            IE = {})
    public void entries() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.Symbol) {\n"
            + "      var myMap = new Map();\n"
            + "      myMap.set('0', 'foo');\n"
            + "      myMap.set(1, 'bar');\n"
            + "      myMap.set({}, 'baz');\n"
            + "      alert(myMap.entries);\n"
            + "      var iter = myMap.entries();\n"
            + "      alert(iter);\n"
            + "      alert(iter.next().value);\n"
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
                "[object Map Iterator]", "foo", "bar", "baz", "undefined"},
            FF = {"function values() {\n    [native code]\n}",
                "[object Map Iterator]", "foo", "bar", "baz", "undefined"},
            IE = {})
    public void values() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.Symbol) {\n"
            + "      var myMap = new Map();\n"
            + "      myMap.set('0', 'foo');\n"
            + "      myMap.set(1, 'bar');\n"
            + "      myMap.set({}, 'baz');\n"
            + "      alert(myMap.values);\n"
            + "      var iter = myMap.values();\n"
            + "      alert(iter);\n"
            + "      alert(iter.next().value);\n"
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
    @Alerts(DEFAULT = {"function keys() { [native code] }",
                "[object Map Iterator]", "0", "1", "[object Object]", "undefined"},
            FF = {"function keys() {\n    [native code]\n}",
                "[object Map Iterator]", "0", "1", "[object Object]", "undefined"},
            IE = {})
    public void keys() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    if (window.Symbol) {\n"
            + "      var myMap = new Map();\n"
            + "      myMap.set('0', 'foo');\n"
            + "      myMap.set(1, 'bar');\n"
            + "      myMap.set({}, 'baz');\n"
            + "      alert(myMap.keys);\n"
            + "      var iter = myMap.keys();\n"
            + "      alert(iter);\n"
            + "      alert(iter.next().value);\n"
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
    @Alerts(DEFAULT = "exception",
            IE = "0")
    public void constructor() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var array = new Int32Array([2, 7]);\n"
            + "  try {\n"
            + "    var myMap = new Map(array);\n"
            + "    alert(myMap.size);\n"
            + "  } catch(e) {\n"
            + "    alert('exception');\n"
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
    @Alerts(DEFAULT = "exception",
            IE = "0")
    public void constructorStringParam() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  try {\n"
            + "    var myMap = new Map('test');\n"
            + "    alert(myMap.size);\n"
            + "  } catch(e) {\n"
            + "    alert('exception');\n"
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
    @Alerts(DEFAULT = "exception",
            IE = "0")
    public void constructorSetParam() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  try {\n"
            + "    var myMap = new Map(new Set('test'));\n"
            + "    alert(myMap.size);\n"
            + "  } catch(e) {\n"
            + "    alert('exception');\n"
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
    @Alerts(DEFAULT = "2",
            IE = "0")
    public void constructorMapParam() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var kvArray = [['key1', 'value1'], ['key2', 'value2']];\n"
            + "  var testMap = new Map(kvArray);\n"
            + "  var myMap = new Map(testMap);\n"
            + "  alert(myMap.size);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"value1", "key1", "[object Map]", "[object Window]",
            "[object Object]", "key2", "[object Map]", "[object Window]",
            "null", "key3", "[object Map]", "[object Window]",
            "undefined", "key4", "[object Map]", "[object Window]"},
            IE = {})
    public void forEach() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function logElement(value, key, m) {\n"
            + "  alert(value);\n"
            + "  alert(key);\n"
            + "  alert(m);\n"
            + "  alert(this);\n"
            + "}\n"
            + "function test() {\n"
            + "try {"
            + "  var myMap = new Map([['key1', 'value1'], ['key2', {}], ['key3', null], ['key4', undefined]]);\n"
            + "  myMap.forEach(logElement);\n"
             + "}catch(e){alert(e)}"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"value1", "key1", "[object Map]", "hello",
            "[object Object]", "key2", "[object Map]", "hello",
            "null", "key3", "[object Map]", "hello",
            "undefined", "key4", "[object Map]", "hello"},
            IE = {})
    @NotYetImplemented({CHROME, FF})
    public void forEachThis() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "function logElement(value, key, m) {\n"
            + "  alert(value);\n"
            + "  alert(key);\n"
            + "  alert(m);\n"
            + "  alert(this);\n"
            + "}\n"
            + "function test() {\n"
            + "  var myMap = new Map([['key1', 'value1'], ['key2', {}], ['key3', null], ['key4', undefined]]);\n"
            + "  myMap.forEach(logElement, 'hello');\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

}
