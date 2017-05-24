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

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;

/**
 * Tests for {@link HTMLTemplateElement}.
 *
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class HTMLTemplateElementTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "false",
            IE = "exception")
    public void prototype() throws Exception {
        final String html
            = "<html><body>\n"
            + "<script>\n"
            + "  try {\n"
            + "    alert(HTMLTemplateElement.prototype == null);\n"
            + "  } catch (e) { alert('exception'); }\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "true",
            IE = "false")
    public void contentCheck() throws Exception {
        final String html =
            "<html>\n"
            + "  <head>\n"
            + "    <script>\n"
            + "      function test() {\n"
            + "        try {\n"
            + "          var template = document.createElement('template');\n"
            + "          alert('content' in template);\n"
            + "        } catch (e) { alert('exception'); }\n"
            + "      }\n"
            + "    </script>\n"
            + "  </head>\n"
            + "  <body onload='test()'>\n"
            + "  </body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object DocumentFragment]", "0-0",
                        "[object DocumentFragment]", "0-0",
                        "[object DocumentFragment]", "0-1",
                        "[object DocumentFragment]", "0-2"},
            IE = "not available")
    public void content() throws Exception {
        final String html =
            "<html>\n"
            + "  <head>\n"
            + "    <script>\n"
            + "      function test() {\n"
            + "        var template = document.createElement('template');\n"
            + "        if (!('content' in template)) { alert('not available'); return }\n"
            + "        alert(template.content);\n"
            + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

            + "        template = document.getElementById('tEmpty');\n"
            + "        alert(template.content);\n"
            + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

            + "        template = document.getElementById('tText');\n"
            + "        alert(template.content);\n"
            + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

            + "        template = document.getElementById('tDiv');\n"
            + "        alert(template.content);\n"
            + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"
            + "      }\n"
            + "    </script>\n"
            + "  </head>\n"
            + "  <body onload='test()'>\n"
            + "    <template id='tEmpty'></template>\n"
            + "    <template id='tText'>HtmlUnit</template>\n"
            + "    <template id='tDiv'><div>HtmlUnit</div><div>is great</div></template>\n"
            + "  </body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object DocumentFragment]", "0-0", "1-0"},
            IE = "not available")
    public void appendChild() throws Exception {
        final String html =
            "<html>\n"
            + "  <head>\n"
            + "    <script>\n"
            + "      function test() {\n"
            + "        var template = document.createElement('template');\n"
            + "        if (!('content' in template)) { alert('not available'); return }\n"

            + "        template = document.getElementById('tester');\n"
            + "        alert(template.content);\n"
            + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

            + "        var div = document.createElement('div');\n"
            + "        template.appendChild(div);\n"
            + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"
            + "      }\n"
            + "    </script>\n"
            + "  </head>\n"
            + "  <body onload='test()'>\n"
            + "    <template id='tester'></template>\n"
            + "  </body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"", "", "<p></p>", "", "HtmlUnit", "<div>HtmlUnit</div><div>is great</div>"},
            IE = {"", "<div></div>", "", "HtmlUnit", "<div>HtmlUnit</div><div>is great</div>"})
    public void innerHTML() throws Exception {
        final String html =
            "<html>\n"
            + "  <head>\n"
            + "    <script>\n"
            + "      function test() {\n"
            + "        var template = document.createElement('template');\n"
            + "        alert(template.innerHTML);\n"

            + "        var div = document.createElement('div');\n"
            + "        template.appendChild(div);\n"
            + "        alert(template.innerHTML);\n"

            + "        var p = document.createElement('p');\n"
            + "        if ('content' in template) {\n"
            + "          template.content.appendChild(p);\n"
            + "          alert(template.innerHTML);\n"
            + "        }\n"

            + "        template = document.getElementById('tEmpty');\n"
            + "        alert(template.innerHTML);\n"

            + "        template = document.getElementById('tText');\n"
            + "        alert(template.innerHTML);\n"

            + "        template = document.getElementById('tDiv');\n"
            + "        alert(template.innerHTML);\n"
            + "      }\n"
            + "    </script>\n"
            + "  </head>\n"
            + "  <body onload='test()'>\n"
            + "    <template id='tEmpty'></template>\n"
            + "    <template id='tText'>HtmlUnit</template>\n"
            + "    <template id='tDiv'><div>HtmlUnit</div><div>is great</div></template>\n"
            + "  </body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }
}
