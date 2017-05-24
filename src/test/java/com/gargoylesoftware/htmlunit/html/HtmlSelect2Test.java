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
package com.gargoylesoftware.htmlunit.html;

import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.FF;
import static com.gargoylesoftware.htmlunit.BrowserRunner.Browser.IE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.BuggyWebDriver;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;

/**
 * Tests for {@link HtmlSelect}.
 *
 * @author Ahmed Ashour
 */
@RunWith(BrowserRunner.class)
public class HtmlSelect2Test extends WebDriverTestCase {

    /**
     * @exception Exception If the test fails
     */
    @Test
    @BuggyWebDriver({FF, IE})
    public void select() throws Exception {
        final String html = "<html><head><title>foo</title></head><body>\n"
            + "<form id='form1'><select name='select1' multiple>\n"
            + "  <option value='option1'>Option1</option>\n"
            + "  <option value='option2'>Option2</option>\n"
            + "  <option value='option3' selected='selected'>Option3</option>\n"
            + "  <option value='option4'>Option4</option>\n"
            + "</select>\n"
            + "<input type='submit' name='button' value='foo'/>\n"
            + "</form></body></html>";

        final WebDriver driver = loadPage2(html);

        final List<WebElement> options = driver.findElements(By.tagName("option"));

        final Actions actions = new Actions(driver);
        final Action selectThreeOptions = actions.click(options.get(1))
            .click(options.get(2))
            .click(options.get(3))
            .build();

        selectThreeOptions.perform();

        assertFalse(options.get(0).isSelected());
        assertFalse(options.get(1).isSelected());
        assertFalse(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());
    }

    /**
     * @exception Exception If the test fails
     */
    @Test
    @BuggyWebDriver(IE)
    public void shiftClick() throws Exception {
        final String html = "<html><head><title>foo</title></head><body>\n"
            + "<form id='form1'><select name='select1' multiple>\n"
            + "  <option value='option1'>Option1</option>\n"
            + "  <option value='option2'>Option2</option>\n"
            + "  <option value='option3' selected='selected'>Option3</option>\n"
            + "  <option value='option4'>Option4</option>\n"
            + "</select>\n"
            + "<input type='submit' name='button' value='foo'/>\n"
            + "</form></body></html>";

        final WebDriver driver = loadPage2(html);

        final List<WebElement> options = driver.findElements(By.tagName("option"));

        final Actions actions = new Actions(driver);
        final Action selectThreeOptions = actions.click(options.get(1))
                .keyDown(Keys.SHIFT)
                .click(options.get(3))
                .keyUp(Keys.SHIFT)
                .build();

        selectThreeOptions.perform();

        assertFalse(options.get(0).isSelected());
        assertTrue(options.get(1).isSelected());
        assertTrue(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());
    }

    /**
     * @exception Exception If the test fails
     */
    @Test
    @BuggyWebDriver({FF, IE})
    public void controlClick() throws Exception {
        final String html = "<html><head><title>foo</title></head><body>\n"
            + "<form id='form1'><select name='select1' multiple>\n"
            + "  <option value='option1'>Option1</option>\n"
            + "  <option value='option2'>Option2</option>\n"
            + "  <option value='option3' selected='selected'>Option3</option>\n"
            + "  <option value='option4'>Option4</option>\n"
            + "</select>\n"
            + "<input type='submit' name='button' value='foo'/>\n"
            + "</form></body></html>";

        final WebDriver driver = loadPage2(html);

        final List<WebElement> options = driver.findElements(By.tagName("option"));

        final Actions actions = new Actions(driver);
        final Action selectThreeOptions = actions.click(options.get(1))
                .keyDown(Keys.CONTROL)
                .click(options.get(3))
                .keyUp(Keys.CONTROL)
                .build();

        selectThreeOptions.perform();

        assertFalse(options.get(0).isSelected());
        assertTrue(options.get(1).isSelected());
        assertFalse(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());
    }
}
