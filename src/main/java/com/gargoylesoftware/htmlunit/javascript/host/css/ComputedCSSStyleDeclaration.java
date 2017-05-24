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
package com.gargoylesoftware.htmlunit.javascript.host.css;

import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.CSS_COMPUTED_BLOCK_IF_NOT_ATTACHED;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.CSS_COMPUTED_NO_Z_INDEX;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_CLIENTHIGHT_INPUT_17;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_CLIENTWIDTH_INPUT_TEXT_143;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_CLIENTWIDTH_INPUT_TEXT_169;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.ACCELERATOR;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.AZIMUTH;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BACKGROUND_ATTACHMENT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BACKGROUND_COLOR;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BACKGROUND_IMAGE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BACKGROUND_POSITION;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BACKGROUND_REPEAT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_BOTTOM_COLOR;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_BOTTOM_STYLE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_BOTTOM_WIDTH;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_COLLAPSE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_LEFT_COLOR;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_LEFT_STYLE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BORDER_SPACING;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.BOX_SIZING;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.CAPTION_SIDE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.COLOR;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.CSS_FLOAT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.CURSOR;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.DIRECTION;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.DISPLAY;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.ELEVATION;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.EMPTY_CELLS;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT_FAMILY;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT_SIZE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT_STRETCH;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT_STYLE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT_VARIANT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.FONT_WEIGHT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.HEIGHT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LEFT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LETTER_SPACING;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LINE_HEIGHT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LIST_STYLE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LIST_STYLE_IMAGE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LIST_STYLE_POSITION;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.LIST_STYLE_TYPE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.MARGIN;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.MARGIN_LEFT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.MARGIN_RIGHT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.ORPHANS;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.OVERFLOW;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.PADDING;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.PITCH;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.PITCH_RANGE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.POSITION;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.QUOTES;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.RICHNESS;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.SPEAK;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.SPEAK_HEADER;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.SPEAK_NUMERAL;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.SPEAK_PUNCTUATION;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.SPEECH_RATE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.STRESS;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.TEXT_ALIGN;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.TEXT_INDENT;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.TEXT_TRANSFORM;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.TOP;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.VISIBILITY;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.VOICE_FAMILY;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.VOLUME;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.WHITE_SPACE;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.WIDOWS;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.WIDTH;
import static com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition.WORD_SPACING;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.w3c.css.sac.Selector;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.css.SelectorSpecificity;
import com.gargoylesoftware.htmlunit.css.StyleElement;
import com.gargoylesoftware.htmlunit.html.BaseFrameElement;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInlineFrame;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlResetInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.gargoylesoftware.htmlunit.javascript.host.css.StyleAttributes.Definition;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Text;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBodyElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCanvasElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;

import net.sourceforge.htmlunit.corejs.javascript.Context;

/**
 * An object for a CSSStyleDeclaration, which is computed.
 *
 * @see com.gargoylesoftware.htmlunit.javascript.host.Window#getComputedStyle(Object, String)
 *
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Ronald Brill
 * @author Frank Danek
 */
@JsxClass(isJSObject = false, browsers = @WebBrowser(FF))
public class ComputedCSSStyleDeclaration extends CSSStyleDeclaration {

    /** Denotes a value which should be returned as is. */
    private static final String EMPTY_FINAL = new String("");

    /** The number of (horizontal) pixels to assume that each character occupies. */
    private static final int PIXELS_PER_CHAR = 10;

    /** The set of 'inheritable' definitions. */
    private static final Set<Definition> INHERITABLE_DEFINITIONS = EnumSet.of(
        AZIMUTH,
        BORDER_COLLAPSE,
        BORDER_SPACING,
        CAPTION_SIDE,
        COLOR,
        CURSOR,
        DIRECTION,
        ELEVATION,
        EMPTY_CELLS,
        FONT_FAMILY,
        FONT_SIZE,
        FONT_STYLE,
        FONT_VARIANT,
        FONT_WEIGHT,
        FONT,
        LETTER_SPACING,
        LINE_HEIGHT,
        LIST_STYLE_IMAGE,
        LIST_STYLE_POSITION,
        LIST_STYLE_TYPE,
        LIST_STYLE,
        ORPHANS,
        PITCH_RANGE,
        PITCH,
        QUOTES,
        RICHNESS,
        SPEAK_HEADER,
        SPEAK_NUMERAL,
        SPEAK_PUNCTUATION,
        SPEAK,
        SPEECH_RATE,
        STRESS,
        TEXT_ALIGN,
        TEXT_INDENT,
        TEXT_TRANSFORM,
        VISIBILITY,
        VOICE_FAMILY,
        VOICE_FAMILY,
        VOLUME,
        WHITE_SPACE,
        WIDOWS,
        WORD_SPACING);

    /**
     * Local modifications maintained here rather than in the element. We use a sorted
     * map so that results are deterministic and thus easily testable.
     */
    private final SortedMap<String, StyleElement> localModifications_ = new TreeMap<>();

    /** The computed, cached width of the element to which this computed style belongs (no padding, borders, etc). */
    private Integer width_;

    /**
     * The computed, cached height of the element to which this computed style belongs (no padding, borders, etc),
     * taking child elements into account.
     */
    private Integer height_;

    /**
     * The computed, cached height of the element to which this computed style belongs (no padding, borders, etc),
     * <b>not</b> taking child elements into account.
     */
    private Integer height2_;

    /** The computed, cached horizontal padding (left + right) of the element to which this computed style belongs. */
    private Integer paddingHorizontal_;

    /** The computed, cached vertical padding (top + bottom) of the element to which this computed style belongs. */
    private Integer paddingVertical_;

    /** The computed, cached horizontal border (left + right) of the element to which this computed style belongs. */
    private Integer borderHorizontal_;

    /** The computed, cached vertical border (top + bottom) of the element to which this computed style belongs. */
    private Integer borderVertical_;

    /** The computed, cached top of the element to which this computed style belongs. */
    private Integer top_;

    /**
     * Creates an instance.
     */
    public ComputedCSSStyleDeclaration() {
    }

    /**
     * Creates an instance.
     *
     * @param style the original Style
     */
    public ComputedCSSStyleDeclaration(final CSSStyleDeclaration style) {
        super(style.getElement());
        getElement().setDefaults(this);
    }

    /**
     * {@inheritDoc}
     *
     * This method does nothing as the object is read-only.
     */
    @Override
    protected void setStyleAttribute(final String name, final String newValue) {
        // Empty.
    }

    /**
     * Makes a local, "computed", modification to this CSS style.
     *
     * @param declaration the style declaration
     * @param selector the selector determining that the style applies to this element
     */
    public void applyStyleFromSelector(final org.w3c.dom.css.CSSStyleDeclaration declaration, final Selector selector) {
        final BrowserVersion browserVersion = getBrowserVersion();
        final SelectorSpecificity specificity = new SelectorSpecificity(selector);
        for (int k = 0; k < declaration.getLength(); k++) {
            final String name = declaration.item(k);
            final String value = declaration.getPropertyValue(name);
            final String priority = declaration.getPropertyPriority(name);
            if (!"z-index".equals(name) || !browserVersion.hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
                applyLocalStyleAttribute(name, value, priority, specificity);
            }
        }
    }

    private void applyLocalStyleAttribute(final String name, final String newValue, final String priority,
            final SelectorSpecificity specificity) {
        if (!StyleElement.PRIORITY_IMPORTANT.equals(priority)) {
            final StyleElement existingElement = localModifications_.get(name);
            if (existingElement != null) {
                if (StyleElement.PRIORITY_IMPORTANT.equals(existingElement.getPriority())) {
                    return; // can't override a !important rule by a normal rule. Ignore it!
                }
                else if (specificity.compareTo(existingElement.getSpecificity()) < 0) {
                    return; // can't override a rule with a rule having higher specificity
                }
            }
        }
        final StyleElement element = new StyleElement(name, newValue, priority, specificity);
        localModifications_.put(name, element);
    }

    /**
     * Makes a local, "computed", modification to this CSS style that won't override other
     * style attributes of the same name. This method should be used to set default values
     * for style attributes.
     *
     * @param name the name of the style attribute to set
     * @param newValue the value of the style attribute to set
     */
    public void setDefaultLocalStyleAttribute(final String name, final String newValue) {
        final StyleElement element = new StyleElement(name, newValue);
        localModifications_.put(name, element);
    }

    @Override
    protected StyleElement getStyleElement(final String name) {
        final StyleElement existent = super.getStyleElement(name);

        if (localModifications_ != null) {
            final StyleElement localStyleMod = localModifications_.get(name);
            if (localStyleMod == null) {
                return existent;
            }

            if (existent == null) {
                // Local modifications represent either default style elements or style elements
                // defined in stylesheets; either way, they shouldn't overwrite any style
                // elements derived directly from the HTML element's "style" attribute.
                return localStyleMod;
            }

            // replace if !IMPORTANT
            if (StyleElement.PRIORITY_IMPORTANT.equals(localStyleMod.getPriority())) {
                if (StyleElement.PRIORITY_IMPORTANT.equals(existent.getPriority())) {
                    if (existent.getSpecificity().compareTo(localStyleMod.getSpecificity()) < 0) {
                        return localStyleMod;
                    }
                }
                else {
                    return localStyleMod;
                }
            }
        }
        return existent;
    }

    private String defaultIfEmpty(final String str, final StyleAttributes.Definition definition) {
        return defaultIfEmpty(str, definition, false);
    }

    private String defaultIfEmpty(final String str, final StyleAttributes.Definition definition,
            final boolean isPixel) {
        if (!getElement().getDomNodeOrDie().isAttachedToPage()
                && getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
            return EMPTY_FINAL;
        }
        if (str == null || str.isEmpty()) {
            return definition.getDefaultComputedValue(getBrowserVersion());
        }
        if (isPixel) {
            return pixelString(str);
        }
        return str;
    }

    /**
     * @param toReturnIfEmptyOrDefault the value to return if empty or equals the {@code defualtValue}
     * @param defaultValue the default value of the string
     * @return the string, or {@code toReturnIfEmptyOrDefault}
     */
    private String defaultIfEmpty(final String str, final String toReturnIfEmptyOrDefault, final String defaultValue) {
        if (!getElement().getDomNodeOrDie().isAttachedToPage()
                && getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
            return EMPTY_FINAL;
        }
        if (str == null || str.isEmpty() || str.equals(defaultValue)) {
            return toReturnIfEmptyOrDefault;
        }
        return str;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAccelerator() {
        return defaultIfEmpty(getStyleAttribute(ACCELERATOR, false), ACCELERATOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundAttachment() {
        return defaultIfEmpty(super.getBackgroundAttachment(), BACKGROUND_ATTACHMENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundColor() {
        final String value = super.getBackgroundColor();
        if (StringUtils.isEmpty(value)) {
            return BACKGROUND_COLOR.getDefaultComputedValue(getBrowserVersion());
        }
        return toRGBColor(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundImage() {
        return defaultIfEmpty(super.getBackgroundImage(), BACKGROUND_IMAGE);
    }

    /**
     * Gets the {@code backgroundPosition} style attribute.
     * @return the style attribute
     */
    @Override
    public String getBackgroundPosition() {
        return defaultIfEmpty(super.getBackgroundPosition(), BACKGROUND_POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundRepeat() {
        return defaultIfEmpty(super.getBackgroundRepeat(), BACKGROUND_REPEAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottomColor() {
        return defaultIfEmpty(super.getBorderBottomColor(), BORDER_BOTTOM_COLOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottomStyle() {
        return defaultIfEmpty(super.getBorderBottomStyle(), BORDER_BOTTOM_STYLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottomWidth() {
        return pixelString(defaultIfEmpty(super.getBorderBottomWidth(), BORDER_BOTTOM_WIDTH));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeftColor() {
        return defaultIfEmpty(super.getBorderLeftColor(), BORDER_LEFT_COLOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeftStyle() {
        return defaultIfEmpty(super.getBorderLeftStyle(), BORDER_LEFT_STYLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeftWidth() {
        return pixelString(defaultIfEmpty(super.getBorderLeftWidth(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRightColor() {
        return defaultIfEmpty(super.getBorderRightColor(), "rgb(0, 0, 0)", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRightStyle() {
        return defaultIfEmpty(super.getBorderRightStyle(), "none", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRightWidth() {
        return pixelString(defaultIfEmpty(super.getBorderRightWidth(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTopColor() {
        return defaultIfEmpty(super.getBorderTopColor(), "rgb(0, 0, 0)", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTopStyle() {
        return defaultIfEmpty(super.getBorderTopStyle(), "none", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTopWidth() {
        return pixelString(defaultIfEmpty(super.getBorderTopWidth(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBottom() {
        return defaultIfEmpty(super.getBottom(), "auto", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColor() {
        final String value = defaultIfEmpty(super.getColor(), "rgb(0, 0, 0)", null);
        return toRGBColor(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCssFloat() {
        return defaultIfEmpty(super.getCssFloat(), CSS_FLOAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplay() {
        return getDisplay(false);
    }

    /**
     * Returns the {@code display} attribute.
     * @param ignoreBlockIfNotAttached is
     * {@link com.gargoylesoftware.htmlunit.BrowserVersionFeatures#CSS_COMPUTED_BLOCK_IF_NOT_ATTACHED} ignored
     * @return the {@code display} attribute
     */
    public String getDisplay(final boolean ignoreBlockIfNotAttached) {
        // don't use defaultIfEmpty for performance
        // (no need to calculate the default if not empty)
        final Element elem = getElement();
        final DomElement domElem = elem.getDomNodeOrDie();
        boolean changeValueIfEmpty = false;
        if (!domElem.isAttachedToPage()) {
            final BrowserVersion browserVersion = getBrowserVersion();
            if (browserVersion.hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
                return "";
            }
            if (!ignoreBlockIfNotAttached && browserVersion.hasFeature(CSS_COMPUTED_BLOCK_IF_NOT_ATTACHED)) {
                changeValueIfEmpty = true;
            }
        }
        final String value = super.getStyleAttribute(DISPLAY, false);
        if (StringUtils.isEmpty(value)) {
            if (domElem instanceof HtmlElement) {
                final String defaultValue = ((HtmlElement) domElem).getDefaultStyleDisplay().value();
                if (changeValueIfEmpty) {
                    switch (defaultValue) {
                        case "inline":
                        case "inline-block":
                        case "table-caption":
                        case "table-cell":
                        case "table-column":
                        case "table-column-group":
                        case "table-footer-group":
                        case "table-header-group":
                        case "table-row":
                        case "table-row-group":
                        case "list-item":
                        case "ruby":
                            return "block";

                        default:
                    }
                }
                return defaultValue;
            }
            return "";
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFont() {
        if (getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)
                && getElement().getDomNodeOrDie().isAttachedToPage()) {
            return getStyleAttribute(FONT_STYLE, true) + ' ' + getStyleAttribute(FONT_VARIANT, true) + ' '
                    + getStyleAttribute(FONT_WEIGHT, true) + ' ' + getStyleAttribute(FONT_STRETCH, true) + ' '
                    + getFontSize() + ' ' + '/' + ' ' + getStyleAttribute(LINE_HEIGHT, true) + ' '
                    + getStyleAttribute(FONT_FAMILY, true);
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontSize() {
        String value = super.getFontSize();
        if (!value.isEmpty()) {
            value = pixelValue(value) + "px";
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLineHeight() {
        return defaultIfEmpty(super.getLineHeight(), LINE_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontFamily() {
        return defaultIfEmpty(super.getFontFamily(), FONT_FAMILY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeight() {
        final Element elem = getElement();
        if (!elem.getDomNodeOrDie().isAttachedToPage()) {
            if (getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
                return "";
            }
            if (getStyleAttribute(HEIGHT, true).isEmpty()) {
                return "auto";
            }
        }
        final int windowHeight = elem.getWindow().getWebWindow().getInnerHeight();
        return pixelString(elem, new CssValue(0, windowHeight) {
            @Override
            public String get(final ComputedCSSStyleDeclaration style) {
                final String offsetHeight = ((HTMLElement) elem).getOffsetHeight() + "px";
                return defaultIfEmpty(style.getStyleAttribute(HEIGHT, true), offsetHeight, "auto");
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLeft() {
        final String superLeft = super.getLeft();
        if (!superLeft.endsWith("%")) {
            return defaultIfEmpty(superLeft, "auto", null);
        }

        final Element elem = getElement();
        return pixelString(elem, new CssValue(0, 0) {
            @Override
            public String get(final ComputedCSSStyleDeclaration style) {
                if (style.getElement() == elem) {
                    return style.getStyleAttribute(LEFT, true);
                }
                return style.getStyleAttribute(WIDTH, true);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLetterSpacing() {
        return defaultIfEmpty(super.getLetterSpacing(), "normal", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMargin() {
        return defaultIfEmpty(super.getMargin(), MARGIN, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginBottom() {
        return pixelString(defaultIfEmpty(super.getMarginBottom(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginLeft() {
        return getMarginX(super.getMarginLeft(), MARGIN_LEFT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginRight() {
        return getMarginX(super.getMarginRight(), MARGIN_RIGHT);
    }

    private String getMarginX(final String superMarginX, final Definition definition) {
        if (!superMarginX.endsWith("%")) {
            return pixelString(defaultIfEmpty(superMarginX, "0px", null));
        }
        final Element elem = getElement();
        if (!elem.getDomNodeOrDie().isAttachedToPage() && getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
            return "";
        }

        final int windowWidth = elem.getWindow().getWebWindow().getInnerWidth();
        return pixelString(elem, new CssValue(0, windowWidth) {
            @Override
            public String get(final ComputedCSSStyleDeclaration style) {
                if (style.getElement() == elem) {
                    return style.getStyleAttribute(definition, true);
                }
                return style.getStyleAttribute(WIDTH, true);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginTop() {
        return pixelString(defaultIfEmpty(super.getMarginTop(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxHeight() {
        return defaultIfEmpty(super.getMaxHeight(), "none", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxWidth() {
        return defaultIfEmpty(super.getMaxWidth(), "none", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinHeight() {
        return defaultIfEmpty(super.getMinHeight(), "0px", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinWidth() {
        return defaultIfEmpty(super.getMinWidth(), "0px", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpacity() {
        return defaultIfEmpty(super.getOpacity(), "1", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutlineWidth() {
        return defaultIfEmpty(super.getOutlineWidth(), "0px", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPadding() {
        return defaultIfEmpty(super.getPadding(), PADDING, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingBottom() {
        return pixelString(defaultIfEmpty(super.getPaddingBottom(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingLeft() {
        return pixelString(defaultIfEmpty(super.getPaddingLeft(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingRight() {
        return pixelString(defaultIfEmpty(super.getPaddingRight(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingTop() {
        return pixelString(defaultIfEmpty(super.getPaddingTop(), "0px", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRight() {
        return defaultIfEmpty(super.getRight(), "auto", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextIndent() {
        return defaultIfEmpty(super.getTextIndent(), "0px", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTop() {
        final Element elem = getElement();
        if (!elem.getDomNodeOrDie().isAttachedToPage()
                && getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
            return "";
        }
        final String superTop = super.getTop();
        if (!superTop.endsWith("%")) {
            return defaultIfEmpty(superTop, TOP);
        }

        return pixelString(elem, new CssValue(0, 0) {
            @Override
            public String get(final ComputedCSSStyleDeclaration style) {
                if (style.getElement() == elem) {
                    return style.getStyleAttribute(TOP, true);
                }
                return style.getStyleAttribute(HEIGHT, true);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVerticalAlign() {
        return defaultIfEmpty(super.getVerticalAlign(), "baseline", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWidows() {
        return defaultIfEmpty(super.getWidows(), WIDOWS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrphans() {
        return defaultIfEmpty(super.getOrphans(), ORPHANS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPosition() {
        return defaultIfEmpty(super.getPosition(), POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWidth() {
        if ("none".equals(getDisplay())) {
            return "auto";
        }

        final Element elem = getElement();
        if (!elem.getDomNodeOrDie().isAttachedToPage()) {
            if (getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
                return "";
            }
            if (getStyleAttribute(WIDTH, true).isEmpty()) {
                return "auto";
            }
        }

        final int windowWidth = elem.getWindow().getWebWindow().getInnerWidth();
        return pixelString(elem, new CssValue(0, windowWidth) {
            @Override
            public String get(final ComputedCSSStyleDeclaration style) {
                final String value = style.getStyleAttribute(WIDTH, true);
                if (StringUtils.isEmpty(value)) {
                    if ("absolute".equals(getStyleAttribute(POSITION, true))) {
                        final String content = getDomNodeOrDie().getTextContent();
                        // do this only for small content
                        // at least for empty div's this is more correct
                        if (null != content && content.length() < 13) {
                            return (content.length() * 7) + "px";
                        }
                    }

                    int windowDefaultValue = getWindowDefaultValue();
                    if (elem instanceof HTMLBodyElement) {
                        windowDefaultValue -= 16;
                    }
                    return windowDefaultValue + "px";
                }
                else if ("auto".equals(value)) {
                    int windowDefaultValue = getWindowDefaultValue();
                    if (elem instanceof HTMLBodyElement) {
                        windowDefaultValue -= 16;
                    }
                    return windowDefaultValue + "px";
                }

                return value;
            }
        });
    }

    /**
     * Returns the element's width in pixels, possibly including its padding and border.
     * @param includeBorder whether or not to include the border width in the returned value
     * @param includePadding whether or not to include the padding width in the returned value
     * @return the element's width in pixels, possibly including its padding and border
     */
    public int getCalculatedWidth(final boolean includeBorder, final boolean includePadding) {
        if (!getElement().getDomNodeOrNull().isAttachedToPage()) {
            return 0;
        }
        int width = getCalculatedWidth();
        if (!"border-box".equals(getStyleAttribute(BOX_SIZING))) {
            if (includeBorder) {
                width += getBorderHorizontal();
            }
            else if (isScrollable(true, true) && !(getElement() instanceof HTMLBodyElement)
                    && getElement().getDomNodeOrDie().isAttachedToPage()) {
                width -= 17;
            }

            if (includePadding) {
                width += getPaddingHorizontal();
            }
        }
        return width;
    }

    private int getCalculatedWidth() {
        if (width_ != null) {
            return width_.intValue();
        }

        final Element element = getElement();
        final DomNode node = element.getDomNodeOrDie();
        if (!node.mayBeDisplayed()) {
            width_ = Integer.valueOf(0);
            return 0;
        }

        final String display = getDisplay();
        if ("none".equals(display)) {
            width_ = Integer.valueOf(0);
            return 0;
        }

        final int windowWidth = element.getWindow().getWebWindow().getInnerWidth();

        final int width;
        final String styleWidth = super.getWidth();
        final DomNode parent = node.getParentNode();

        // width is ignored for inline elements
        if (("inline".equals(display) || StringUtils.isEmpty(styleWidth)) && parent instanceof HtmlElement) {
            // hack: TODO find a way to specify default values for different tags
            if (element instanceof HTMLCanvasElement) {
                return 300;
            }

            // Width not explicitly set.
            final String cssFloat = getCssFloat();
            if ("right".equals(cssFloat) || "left".equals(cssFloat)
                    || "absolute".equals(getStyleAttribute(POSITION, true))) {
                // We're floating; simplistic approximation: text content * pixels per character.
                width = node.getTextContent().length() * PIXELS_PER_CHAR;
            }
            else if ("block".equals(display)) {
                if (element instanceof HTMLBodyElement) {
                    width = windowWidth - 16;
                }
                else {
                    // Block elements take up 100% of the parent's width.
                    final HTMLElement parentJS = (HTMLElement) parent.getScriptableObject();
                    width = pixelValue(parentJS, new CssValue(0, windowWidth) {
                        @Override public String get(final ComputedCSSStyleDeclaration style) {
                            return style.getWidth();
                        }
                    }) - (getBorderHorizontal() + getPaddingHorizontal());
                }
            }
            else if (node instanceof HtmlSubmitInput || node instanceof HtmlResetInput
                        || node instanceof HtmlButtonInput || node instanceof HtmlButton
                        || node instanceof HtmlFileInput) {
                final String text = node.asText();
                // default font for buttons is a bit smaller than the body font size
                width = 10 + (int) (text.length() * PIXELS_PER_CHAR * 0.9);
            }
            else if (node instanceof HtmlTextInput || node instanceof HtmlPasswordInput) {
                final BrowserVersion browserVersion = getBrowserVersion();
                if (browserVersion.hasFeature(JS_CLIENTWIDTH_INPUT_TEXT_143)) {
                    return 143;
                }
                if (browserVersion.hasFeature(JS_CLIENTWIDTH_INPUT_TEXT_169)) {
                    return 169;
                }
                width = 141; // FF45
            }
            else if (node instanceof HtmlRadioButtonInput || node instanceof HtmlCheckBoxInput) {
                width = 13;
            }
            else if (node instanceof HtmlTextArea) {
                width = 100; // wild guess
            }
            else {
                // Inline elements take up however much space is required by their children.
                width = getContentWidth();
            }
        }
        else if ("auto".equals(styleWidth)) {
            width = windowWidth;
        }
        else {
            // Width explicitly set in the style attribute, or there was no parent to provide guidance.
            width = pixelValue(element, new CssValue(0, windowWidth) {
                @Override public String get(final ComputedCSSStyleDeclaration style) {
                    return style.getStyleAttribute(WIDTH, true);
                }
            });
        }

        width_ = Integer.valueOf(width);
        return width;
    }

    /**
     * Returns the total width of the element's children.
     * @return the total width of the element's children
     */
    public int getContentWidth() {
        int width = 0;
        final DomNode domNode = getDomNodeOrDie();
        Iterable<DomNode> children = domNode.getChildren();
        if (domNode instanceof BaseFrameElement) {
            final Page enclosedPage = ((BaseFrameElement) domNode).getEnclosedPage();
            if (enclosedPage != null && enclosedPage.isHtmlPage()) {
                final HtmlPage htmlPage = (HtmlPage) enclosedPage;
                children = htmlPage.getChildren();
            }
        }
        for (final DomNode child : children) {
            if (child.getScriptableObject() instanceof HTMLElement) {
                final HTMLElement e = (HTMLElement) child.getScriptableObject();
                final ComputedCSSStyleDeclaration style = e.getWindow().getComputedStyle(e, null);
                final int w = style.getCalculatedWidth(true, true);
                width += w;
            }
            else if (child.getScriptableObject() instanceof Text) {
                final DomNode parent = child.getParentNode();
                if (parent instanceof HtmlElement) {
                    final HTMLElement e = (HTMLElement) child.getParentNode().getScriptableObject();
                    final ComputedCSSStyleDeclaration style = e.getWindow().getComputedStyle(e, null);
                    final int height = ComputedHeight.getHeight(getBrowserVersion(), style.getFontSize());
                    width += child.getTextContent().length() * (int) (height / 1.8f);
                }
                else {
                    width += child.getTextContent().length() * PIXELS_PER_CHAR;
                }
            }
        }
        return width;
    }

    /**
     * Returns the element's height, possibly including its padding and border.
     * @param includeBorder whether or not to include the border height in the returned value
     * @param includePadding whether or not to include the padding height in the returned value
     * @return the element's height, possibly including its padding and border
     */
    public int getCalculatedHeight(final boolean includeBorder, final boolean includePadding) {
        if (!getElement().getDomNodeOrNull().isAttachedToPage()) {
            return 0;
        }
        int height = getCalculatedHeight();
        if (!"border-box".equals(getStyleAttribute(BOX_SIZING))) {
            if (includeBorder) {
                height += getBorderVertical();
            }
            else if (isScrollable(false, true) && !(getElement() instanceof HTMLBodyElement)
                    && getElement().getDomNodeOrDie().isAttachedToPage()) {
                height -= 17;
            }

            if (includePadding) {
                height += getPaddingVertical();
            }
        }
        return height;
    }

    /**
     * Returns the element's calculated height, taking both relevant CSS and the element's children into account.
     * @return the element's calculated height, taking both relevant CSS and the element's children into account
     */
    private int getCalculatedHeight() {
        if (height_ != null) {
            return height_.intValue();
        }

        final int elementHeight = getEmptyHeight();
        if (elementHeight == 0) {
            height_ = Integer.valueOf(elementHeight);
            return elementHeight;
        }

        final int contentHeight = getContentHeight();
        final boolean explicitHeightSpecified = !super.getHeight().isEmpty();

        final int height;
        if (contentHeight > 0 && !explicitHeightSpecified) {
            height = contentHeight;
        }
        else {
            height = elementHeight;
        }

        height_ = Integer.valueOf(height);
        return height;
    }

    /**
     * Returns the element's calculated height taking relevant CSS into account, but <b>not</b> the element's child
     * elements.
     *
     * @return the element's calculated height taking relevant CSS into account, but <b>not</b> the element's child
     *         elements
     */
    private int getEmptyHeight() {
        if (height2_ != null) {
            return height2_.intValue();
        }

        final DomNode node = getElement().getDomNodeOrDie();
        if (!node.mayBeDisplayed()) {
            height2_ = Integer.valueOf(0);
            return 0;
        }

        if ("none".equals(getDisplay())) {
            height2_ = Integer.valueOf(0);
            return 0;
        }

        final Element elem = getElement();
        final int windowHeight = elem.getWindow().getWebWindow().getInnerHeight();

        if (elem instanceof HTMLBodyElement) {
            height2_ = windowHeight;
            return windowHeight;
        }

        final boolean explicitHeightSpecified = !super.getHeight().isEmpty();

        int defaultHeight;
        if (node instanceof HtmlDivision && node.getTextContent().trim().isEmpty()) {
            defaultHeight = 0;
        }
        else if (elem.getFirstChild() == null) {
            if (node instanceof HtmlRadioButtonInput || node instanceof HtmlCheckBoxInput) {
                defaultHeight = 13;
            }
            else if (node instanceof HtmlButton) {
                defaultHeight = 20;
            }
            else if (node instanceof HtmlInput && !(node instanceof HtmlHiddenInput)) {
                if (getBrowserVersion().hasFeature(JS_CLIENTHIGHT_INPUT_17)) {
                    defaultHeight = 17;
                }
                else {
                    defaultHeight = 21;
                }
            }
            else if (node instanceof HtmlSelect) {
                defaultHeight = 20;
            }
            else if (node instanceof HtmlTextArea) {
                defaultHeight = 49;
            }
            else if (node instanceof HtmlInlineFrame) {
                defaultHeight = 154;
            }
            else {
                defaultHeight = 0;
            }
        }
        else {
            defaultHeight = ComputedHeight.getHeight(getBrowserVersion(), getFontSize());
            if (node instanceof HtmlDivision) {
                defaultHeight *= StringUtils.countMatches(node.asText(), '\n') + 1;
            }
        }

        final int defaultWindowHeight = elem instanceof HTMLCanvasElement ? 150 : windowHeight;

        int height = pixelValue(elem, new CssValue(defaultHeight, defaultWindowHeight) {
            @Override public String get(final ComputedCSSStyleDeclaration style) {
                final Element element = style.getElement();
                if (element instanceof HTMLBodyElement) {
                    return String.valueOf(element.getWindow().getWebWindow().getInnerHeight());
                }
                return style.getStyleAttribute(HEIGHT, true);
            }
        });

        if (height == 0 && !explicitHeightSpecified) {
            height = defaultHeight;
        }

        height2_ = Integer.valueOf(height);
        return height;
    }

    /**
     * Returns the total height of the element's children.
     * @return the total height of the element's children
     */
    public int getContentHeight() {
        // There are two different kinds of elements that might determine the content height:
        //  - elements with position:static or position:relative (elements that flow and build on each other)
        //  - elements with position:absolute (independent elements)

        final DomNode node = getElement().getDomNodeOrDie();
        if (!node.mayBeDisplayed()) {
            return 0;
        }

        ComputedCSSStyleDeclaration lastFlowing = null;
        final Set<ComputedCSSStyleDeclaration> styles = new HashSet<>();
        for (final DomNode child : node.getChildren()) {
            if (child.mayBeDisplayed()) {
                final Object scriptObj = child.getScriptableObject();
                if (scriptObj instanceof HTMLElement) {
                    final HTMLElement e = (HTMLElement) scriptObj;
                    final ComputedCSSStyleDeclaration style = e.getWindow().getComputedStyle(e, null);
                    final String pos = style.getPositionWithInheritance();
                    if ("static".equals(pos) || "relative".equals(pos)) {
                        lastFlowing = style;
                    }
                    else if ("absolute".equals(pos)) {
                        styles.add(style);
                    }
                }
            }
        }

        if (lastFlowing != null) {
            styles.add(lastFlowing);
        }

        int max = 0;
        for (final ComputedCSSStyleDeclaration style : styles) {
            final int h = style.getTop(true, false, false) + style.getCalculatedHeight(true, true);
            if (h > max) {
                max = h;
            }
        }
        return max;
    }

    /**
     * Returns {@code true} if the element is scrollable along the specified axis.
     * @param horizontal if {@code true}, the caller is interested in scrollability along the x-axis;
     *        if {@code false}, the caller is interested in scrollability along the y-axis
     * @return {@code true} if the element is scrollable along the specified axis
     */
    public boolean isScrollable(final boolean horizontal) {
        return isScrollable(horizontal, false);
    }

    /**
     * @param ignoreSize whether to consider the content/calculated width/height
     */
    private boolean isScrollable(final boolean horizontal, final boolean ignoreSize) {
        final boolean scrollable;
        final Element node = getElement();
        final String overflow = getStyleAttribute(OVERFLOW, true);
        if (horizontal) {
            // TODO: inherit, overflow-x
            scrollable = (node instanceof HTMLBodyElement || "scroll".equals(overflow) || "auto".equals(overflow))
                && (ignoreSize || getContentWidth() > getCalculatedWidth());
        }
        else {
            // TODO: inherit, overflow-y
            scrollable = (node instanceof HTMLBodyElement || "scroll".equals(overflow) || "auto".equals(overflow))
                && (ignoreSize || getContentHeight() > getEmptyHeight());
        }
        return scrollable;
    }

    /**
     * Returns the computed top (Y coordinate), relative to the node's parent's top edge.
     * @param includeMargin whether or not to take the margin into account in the calculation
     * @param includeBorder whether or not to take the border into account in the calculation
     * @param includePadding whether or not to take the padding into account in the calculation
     * @return the computed top (Y coordinate), relative to the node's parent's top edge
     */
    public int getTop(final boolean includeMargin, final boolean includeBorder, final boolean includePadding) {
        int top = 0;
        if (null == top_) {
            final String p = getPositionWithInheritance();
            if ("absolute".equals(p)) {
                top = getTopForAbsolutePositionWithInheritance();
            }
            else {
                // Calculate the vertical displacement caused by *previous* siblings.
                DomNode prev = getElement().getDomNodeOrDie().getPreviousSibling();
                boolean prevHadComputedTop = false;
                while (prev != null && !prevHadComputedTop) {
                    if (prev instanceof HtmlElement) {
                        final HTMLElement e = (HTMLElement) ((HtmlElement) prev).getScriptableObject();
                        final ComputedCSSStyleDeclaration style = e.getWindow().getComputedStyle(e, null);
                        int prevTop = 0;
                        if (style.top_ == null) {
                            final String prevPosition = style.getPositionWithInheritance();
                            if ("absolute".equals(prevPosition)) {
                                prevTop += style.getTopForAbsolutePositionWithInheritance();
                            }
                            else {
                                if ("relative".equals(prevPosition)) {
                                    final String t = style.getTopWithInheritance();
                                    prevTop += pixelValue(t);
                                }
                            }
                        }
                        else {
                            prevHadComputedTop = true;
                            prevTop += style.top_;
                        }
                        prevTop += style.getCalculatedHeight(true, true);
                        final int margin = pixelValue(style.getMarginTop());
                        prevTop += margin;
                        top += prevTop;
                    }
                    prev = prev.getPreviousSibling();
                }
                // If the position is relative, we also need to add the specified "top" displacement.
                if ("relative".equals(p)) {
                    final String t = getTopWithInheritance();
                    top += pixelValue(t);
                }
            }
            top_ = Integer.valueOf(top);
        }
        else {
            top = top_.intValue();
        }

        if (includeMargin) {
            final int margin = pixelValue(getMarginTop());
            top += margin;
        }

        if (includeBorder) {
            final int border = pixelValue(getBorderTopWidth());
            top += border;
        }

        if (includePadding) {
            final int padding = getPaddingTopValue();
            top += padding;
        }

        return top;
    }

    private int getTopForAbsolutePositionWithInheritance() {
        int top = 0;
        final String t = getTopWithInheritance();

        if (!"auto".equals(t)) {
            // No need to calculate displacement caused by sibling nodes.
            top = pixelValue(t);
        }
        else {
            final String b = getBottomWithInheritance();

            if (!"auto".equals(b)) {
                // Estimate the vertical displacement caused by *all* siblings.
                // This is very rough, and doesn't even take position or display types into account.
                // It also doesn't take into account the fact that the parent's height may be hardcoded in CSS.
                top = 0;
                DomNode child = getElement().getDomNodeOrDie().getParentNode().getFirstChild();
                while (child != null) {
                    if (child instanceof HtmlElement && child.mayBeDisplayed()) {
                        top += 20;
                    }
                    child = child.getNextSibling();
                }
                top -= pixelValue(b);
            }
        }
        return top;
    }

    /**
     * Returns the computed left (X coordinate), relative to the node's parent's left edge.
     * @param includeMargin whether or not to take the margin into account in the calculation
     * @param includeBorder whether or not to take the border into account in the calculation
     * @param includePadding whether or not to take the padding into account in the calculation
     * @return the computed left (X coordinate), relative to the node's parent's left edge
     */
    public int getLeft(final boolean includeMargin, final boolean includeBorder, final boolean includePadding) {
        final String p = getPositionWithInheritance();
        final String l = getLeftWithInheritance();
        final String r = getRightWithInheritance();

        int left;
        if ("absolute".equals(p) && !"auto".equals(l)) {
            // No need to calculate displacement caused by sibling nodes.
            left = pixelValue(l);
        }
        else if ("absolute".equals(p) && !"auto".equals(r)) {
            // Need to calculate the horizontal displacement caused by *all* siblings.
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
            final int parentWidth = style.getCalculatedWidth(false, false);
            left = parentWidth - pixelValue(r);
        }
        else if ("fixed".equals(p) && !"auto".equals(r)) {
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            final ComputedCSSStyleDeclaration style = getWindow().getComputedStyle(getElement(), null);
            final ComputedCSSStyleDeclaration parentStyle = parent.getWindow().getComputedStyle(parent, null);
            left = pixelValue(parentStyle.getWidth()) - pixelValue(style.getWidth()) - pixelValue(r);
        }
        else if ("fixed".equals(p) && "auto".equals(l)) {
            // Fixed to the location at which the browser puts it via normal element flowing.
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
            left = pixelValue(style.getLeftWithInheritance());
        }
        else if ("static".equals(p)) {
            // We need to calculate the horizontal displacement caused by *previous* siblings.
            left = 0;
            for (DomNode n = getDomNodeOrDie(); n != null; n = n.getPreviousSibling()) {
                if (n.getScriptableObject() instanceof HTMLElement) {
                    final HTMLElement e = (HTMLElement) n.getScriptableObject();
                    final ComputedCSSStyleDeclaration style = e.getWindow().getComputedStyle(e, null);
                    final String d = style.getDisplay();
                    if ("block".equals(d)) {
                        break;
                    }
                    else if (!"none".equals(d)) {
                        left += style.getCalculatedWidth(true, true);
                    }
                }
                else if (n.getScriptableObject() instanceof Text) {
                    left += n.getTextContent().length() * PIXELS_PER_CHAR;
                }
                if (n instanceof HtmlTableRow) {
                    break;
                }
            }
        }
        else {
            // Just use the CSS specified value.
            left = pixelValue(l);
        }

        if (includeMargin) {
            final int margin = getMarginLeftValue();
            left += margin;
        }

        if (includeBorder) {
            final int border = pixelValue(getBorderLeftWidth());
            left += border;
        }

        if (includePadding) {
            final int padding = getPaddingLeftValue();
            left += padding;
        }

        return left;
    }

    /**
     * Returns the CSS {@code position} attribute, replacing inherited values with the actual parent values.
     * @return the CSS {@code position} attribute, replacing inherited values with the actual parent values
     */
    public String getPositionWithInheritance() {
        String p = getStyleAttribute(POSITION, true);
        if ("inherit".equals(p)) {
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            if (parent == null) {
                p = "static";
            }
            else {
                final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
                p = style.getPositionWithInheritance();
            }
        }
        return p;
    }

    /**
     * Returns the CSS {@code left} attribute, replacing inherited values with the actual parent values.
     * @return the CSS {@code left} attribute, replacing inherited values with the actual parent values
     */
    public String getLeftWithInheritance() {
        String left = getLeft();
        if ("inherit".equals(left)) {
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            if (parent == null) {
                left = "auto";
            }
            else {
                final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
                left = style.getLeftWithInheritance();
            }
        }
        return left;
    }

    /**
     * Returns the CSS {@code right} attribute, replacing inherited values with the actual parent values.
     * @return the CSS {@code right} attribute, replacing inherited values with the actual parent values
     */
    public String getRightWithInheritance() {
        String right = getRight();
        if ("inherit".equals(right)) {
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            if (parent == null) {
                right = "auto";
            }
            else {
                final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
                right = style.getRightWithInheritance();
            }
        }
        return right;
    }

    /**
     * Returns the CSS {@code top} attribute, replacing inherited values with the actual parent values.
     * @return the CSS {@code top} attribute, replacing inherited values with the actual parent values
     */
    public String getTopWithInheritance() {
        String top = getTop();
        if ("inherit".equals(top)) {
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            if (parent == null) {
                top = "auto";
            }
            else {
                final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
                top = style.getTopWithInheritance();
            }
        }
        return top;
    }

    /**
     * Returns the CSS {@code bottom} attribute, replacing inherited values with the actual parent values.
     * @return the CSS {@code bottom} attribute, replacing inherited values with the actual parent values
     */
    public String getBottomWithInheritance() {
        String bottom = getBottom();
        if ("inherit".equals(bottom)) {
            final HTMLElement parent = (HTMLElement) getElement().getParentElement();
            if (parent == null) {
                bottom = "auto";
            }
            else {
                final ComputedCSSStyleDeclaration style = parent.getWindow().getComputedStyle(parent, null);
                bottom = style.getBottomWithInheritance();
            }
        }
        return bottom;
    }

    /**
     * Gets the left margin of the element.
     * @return the value in pixels
     */
    public int getMarginLeftValue() {
        return pixelValue(getMarginLeft());
    }

    /**
     * Gets the right margin of the element.
     * @return the value in pixels
     */
    public int getMarginRightValue() {
        return pixelValue(getMarginRight());
    }

    /**
     * Gets the top margin of the element.
     * @return the value in pixels
     */
    public int getMarginTopValue() {
        return pixelValue(getMarginTop());
    }

    /**
     * Gets the bottom margin of the element.
     * @return the value in pixels
     */
    public int getMarginBottomValue() {
        return pixelValue(getMarginBottom());
    }

    /**
     * Gets the left padding of the element.
     * @return the value in pixels
     */
    public int getPaddingLeftValue() {
        return pixelValue(getPaddingLeft());
    }

    /**
     * Gets the right padding of the element.
     * @return the value in pixels
     */
    public int getPaddingRightValue() {
        return pixelValue(getPaddingRight());
    }

    /**
     * Gets the top padding of the element.
     * @return the value in pixels
     */
    public int getPaddingTopValue() {
        return pixelValue(getPaddingTop());
    }

    /**
     * Gets the bottom padding of the element.
     * @return the value in pixels
     */
    public int getPaddingBottomValue() {
        return pixelValue(getPaddingBottom());
    }

    private int getPaddingHorizontal() {
        if (paddingHorizontal_ == null) {
            paddingHorizontal_ =
                Integer.valueOf("none".equals(getDisplay()) ? 0 : getPaddingLeftValue() + getPaddingRightValue());
        }
        return paddingHorizontal_.intValue();
    }

    private int getPaddingVertical() {
        if (paddingVertical_ == null) {
            paddingVertical_ =
                Integer.valueOf("none".equals(getDisplay()) ? 0 : getPaddingTopValue() + getPaddingBottomValue());
        }
        return paddingVertical_.intValue();
    }

    /**
     * Gets the size of the left border of the element.
     * @return the value in pixels
     */
    public int getBorderLeftValue() {
        return pixelValue(getBorderLeftWidth());
    }

    /**
     * Gets the size of the right border of the element.
     * @return the value in pixels
     */
    public int getBorderRightValue() {
        return pixelValue(getBorderRightWidth());
    }

    /**
     * Gets the size of the top border of the element.
     * @return the value in pixels
     */
    public int getBorderTopValue() {
        return pixelValue(getBorderTopWidth());
    }

    /**
     * Gets the size of the bottom border of the element.
     * @return the value in pixels
     */
    public int getBorderBottomValue() {
        return pixelValue(getBorderBottomWidth());
    }

    private int getBorderHorizontal() {
        if (borderHorizontal_ == null) {
            borderHorizontal_ =
                Integer.valueOf("none".equals(getDisplay()) ? 0 : getBorderLeftValue() + getBorderRightValue());
        }
        return borderHorizontal_.intValue();
    }

    private int getBorderVertical() {
        if (borderVertical_ == null) {
            borderVertical_ =
                Integer.valueOf("none".equals(getDisplay()) ? 0 : getBorderTopValue() + getBorderBottomValue());
        }
        return borderVertical_.intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWordSpacing() {
        return defaultIfEmpty(super.getWordSpacing(), WORD_SPACING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStyleAttribute(final Definition style, final boolean getDefaultValueIfEmpty) {
        if (!getElement().getDomNodeOrDie().isAttachedToPage()
                && getBrowserVersion().hasFeature(CSS_COMPUTED_NO_Z_INDEX)) {
            return EMPTY_FINAL;
        }
        String value = super.getStyleAttribute(style, getDefaultValueIfEmpty);
        if (value.isEmpty()) {
            final Element parent = getElement().getParentElement();
            if (INHERITABLE_DEFINITIONS.contains(style) && parent != null) {
                value = getWindow().getComputedStyle(parent, null).getStyleAttribute(style, getDefaultValueIfEmpty);
            }
            else if (getDefaultValueIfEmpty) {
                value = style.getDefaultComputedValue(getBrowserVersion());
            }
        }

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getZIndex() {
        final Object response = super.getZIndex();
        if (response.toString().isEmpty()) {
            return "auto";
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPropertyValue(final String name) {
        // need to invoke the getter to take care of the default value
        final Object property = getProperty(this, camelize(name));
        if (property == NOT_FOUND) {
            return super.getPropertyValue(name);
        }
        return Context.toString(property);
    }

    /**
     * Returns the specified length value as a pixel length value, as long as we're not emulating IE.
     * This method does <b>NOT</b> handle percentages correctly; use {@link #pixelValue(Element, CssValue)}
     * if you need percentage support).
     * @param value the length value to convert to a pixel length value
     * @return the specified length value as a pixel length value
     * @see #pixelString(Element, CSSStyleDeclaration.CssValue)
     */
    protected String pixelString(final String value) {
        if (value == EMPTY_FINAL || value.endsWith("px")) {
            return value;
        }
        return pixelValue(value) + "px";
    }

    /**
     * Returns the specified length CSS attribute value value as a pixel length value, as long as
     * we're not emulating IE. If the specified CSS attribute value is a percentage, this method
     * uses the specified value object to recursively retrieve the base (parent) CSS attribute value.
     * @param element the element for which the CSS attribute value is to be retrieved
     * @param value the CSS attribute value which is to be retrieved
     * @return the specified length CSS attribute value as a pixel length value
     * @see #pixelString(String)
     */
    protected String pixelString(final Element element, final CssValue value) {
        final String s = value.get(element);
        if (s.endsWith("px")) {
            return s;
        }
        return pixelValue(element, value) + "px";
    }

}
