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
package com.gargoylesoftware.htmlunit.javascript.host.dom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlElement;

/**
 * Tests for {@link MutationObserver}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class MutationObserverTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"old", "new"})
    public void characterData() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function test() {\n"
            + "  var div = document.getElementById('myDiv');\n"
            + "  var observer = new MutationObserver(function(mutations) {\n"
            + "    mutations.forEach(function(mutation) {\n"
            + "      alert(mutation.oldValue);\n"
            + "      alert(mutation.target.textContent);\n"
            + "    });\n"
            + "  });\n"
            + "\n"
            + "  observer.observe(div, {\n"
            + "    characterData: true,\n"
            + "    characterDataOldValue: true,\n"
            + "    subtree: true\n"
            + "  });\n"
            + "\n"
            + "  div.firstChild.textContent = 'new';\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'>old</div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "new"})
    public void characterDataNoOldValue() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function test() {\n"
            + "  var div = document.getElementById('myDiv');\n"
            + "  var observer = new MutationObserver(function(mutations) {\n"
            + "    mutations.forEach(function(mutation) {\n"
            + "      alert(mutation.oldValue);\n"
            + "      alert(mutation.target.textContent);\n"
            + "    });\n"
            + "  });\n"
            + "\n"
            + "  observer.observe(div, {\n"
            + "    characterData: true,\n"
            + "    subtree: true\n"
            + "  });\n"
            + "\n"
            + "  div.firstChild.textContent = 'new';\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'>old</div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void characterDataNoSubtree() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function test() {\n"
            + "  var div = document.getElementById('myDiv');\n"
            + "  var observer = new MutationObserver(function(mutations) {\n"
            + "    mutations.forEach(function(mutation) {\n"
            + "      alert(mutation.oldValue);\n"
            + "      alert(mutation.target.textContent);\n"
            + "    });\n"
            + "  });\n"
            + "\n"
            + "  observer.observe(div, {\n"
            + "    characterData: true,\n"
            + "    characterDataOldValue: true\n"
            + "  });\n"
            + "\n"
            + "  div.firstChild.textContent = 'new';\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'>old</div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"attributes", "ltr"})
    public void attributes() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function test() {\n"
            + "  var div = document.getElementById('myDiv');\n"
            + "  var observer = new MutationObserver(function(mutations) {\n"
            + "    mutations.forEach(function(mutation) {\n"
            + "      alert(mutation.type);\n"
            + "      alert(mutation.oldValue);\n"
            + "    });\n"
            + "  });\n"
            + "\n"
            + "  observer.observe(div, {\n"
            + "    attributes: true,\n"
            + "    attributeFilter: ['dir'],\n"
            + "    attributeOldValue: true\n"
            + "  });\n"
            + "\n"
            + "  div.dir = 'rtl';\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv' dir='ltr'>old</div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test case for issue #1811.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("abc")
    public void attributeValue() throws Exception {
        final String html
            = "<html>\n"
            + "<head><script>\n"
            + "  function test() {\n"
            + "    var config = { attributes: true, childList: true, characterData: true, subtree: true };\n"
            + "    var observer = new MutationObserver(function(mutations) {\n"
            + "      mutations.forEach(function(mutation) {\n"
            + "        alert(mutation.target.value);\n"
            + "      });\n"
            + "    });\n"
            + "    observer.observe(document.getElementById('tester'), config);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <input id='tester' value=''>\n"
            + "  <button id='doIt' onclick='document.getElementById(\"tester\").setAttribute(\"value\", \"x\")'>"
                        + "DoIt</button>\n"
            + "</body></html>";
        final WebDriver driver = loadPage2(html);
        driver.findElement(By.id("tester")).sendKeys("abc");
        verifyAlerts(driver, getExpectedAlerts());
        driver.findElement(By.id("doIt")).click();
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("[object HTMLHeadingElement]-attributes")
    public void attributeValue2() throws Exception {
        final String html = "<html><head><script>\n"
            + "  function makeRed() {\n"
            + "    document.getElementById('headline').setAttribute('style', 'color: red');\n"
            + "  }\n"

            + "  function print(mutation) {\n"
            + "    alert(mutation.target + '-' + mutation.type);\n"
            + "  }\n"

            + "  function test() {\n"
            + "    var mobs = new MutationObserver(function(mutations) {\n"
            + "      mutations.forEach(print)\n"
            + "    });\n"

            + "    mobs.observe(document.getElementById('container'), {\n"
            + "      attributes: true,\n"
            + "      childList: true,\n"
            + "      characterData: true,\n"
            + "      subtree: true\n"
            + "    });\n"

            + "    document.addEventListener('beforeunload', function() {\n"
            + "      mobs.disconnect();\n"
            + "    });\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='container'>\n"
            + "    <h1 id='headline' style='font-style: italic'>Some headline</h1>\n"
            + "    <input id='id1' type='button' onclick='makeRed()' value='Make Red'>\n"
            + "  </div>\n"
            + "</body></html>\n";
        final WebDriver driver = loadPage2(html);
        driver.findElement(By.id("id1")).click();
        verifyAlerts(driver, getExpectedAlerts());

        if (driver instanceof HtmlUnitDriver) {
            driver.get(URL_FIRST.toExternalForm());
            final HtmlElement element = toHtmlElement(driver.findElement(By.id("headline")));
            element.setAttribute("style", "color: red");
            verifyAlerts(driver, getExpectedAlerts());
        }
    }
}
