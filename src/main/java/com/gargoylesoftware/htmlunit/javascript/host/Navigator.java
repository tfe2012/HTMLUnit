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
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.IE;

import com.gargoylesoftware.htmlunit.PluginConfiguration;
import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import com.gargoylesoftware.htmlunit.javascript.host.geo.Geolocation;

/**
 * A JavaScript object for {@code Navigator}.
 *
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author Daniel Gredler
 * @author Chris Erskine
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Frank Danek
 *
 * @see <a href="http://msdn.microsoft.com/en-us/library/ms535867.aspx">MSDN documentation</a>
 */
@JsxClass
public class Navigator extends SimpleScriptable {

    private PluginArray plugins_;
    private MimeTypeArray mimeTypes_;

    /**
     * Creates an instance.
     */
    @JsxConstructor({@WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE)})
    public Navigator() {
    }

    /**
     * Returns the {@code appCodeName} property.
     * @return the {@code appCodeName} property
     */
    @JsxGetter
    public String getAppCodeName() {
        return getBrowserVersion().getApplicationCodeName();
    }

    /**
     * Returns the {@code appMinorVersion} property.
     * @return the {@code appMinorVersion} property
     */
    @JsxGetter(@WebBrowser(IE))
    public String getAppMinorVersion() {
        return getBrowserVersion().getApplicationMinorVersion();
    }

    /**
     * Returns the {@code appName} property.
     * @return the {@code appName} property
     */
    @JsxGetter
    public String getAppName() {
        return getBrowserVersion().getApplicationName();
    }

    /**
     * Returns the {@code appVersion} property.
     * @return the {@code appVersion} property
     */
    @JsxGetter
    public String getAppVersion() {
        return getBrowserVersion().getApplicationVersion();
    }

    /**
     * Returns the language of the browser.
     * @return the language
     */
    @JsxGetter(@WebBrowser(IE))
    public String getBrowserLanguage() {
        return getLanguage();
    }

    /**
     * Returns the language of the browser.
     * @return the language
     */
    @JsxGetter
    public String getLanguage() {
        return getBrowserVersion().getBrowserLanguage();
    }

    /**
     * Returns the {@code cookieEnabled} property.
     * @return the {@code cookieEnabled} property
     */
    @JsxGetter
    public boolean getCookieEnabled() {
        return getWindow().getWebWindow().getWebClient().getCookieManager().isCookiesEnabled();
    }

    /**
     * Returns the {@code cpuClass} property.
     * @return the {@code cpuClass} property
     */
    @JsxGetter(@WebBrowser(IE))
    public String getCpuClass() {
        return getBrowserVersion().getCpuClass();
    }

    /**
     * Returns the {@code onLine} property.
     * @return the {@code onLine} property
     */
    @JsxGetter
    public boolean getOnLine() {
        return getBrowserVersion().isOnLine();
    }

    /**
     * Returns the {@code platform} property.
     * @return the {@code platform} property
     */
    @JsxGetter
    public String getPlatform() {
        return getBrowserVersion().getPlatform();
    }

    /**
     * Returns the {@code product} property.
     * @return the {@code product} property
     */
    @JsxGetter
    public String getProduct() {
        return "Gecko";
    }

    /**
     * Returns the build number of the current browser.
     * @see <a href="https://developer.mozilla.org/en/navigator.productSub">Mozilla Doc</a>
     * @return false
     */
    @JsxGetter({@WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE)})
    public String getProductSub() {
        return "20100215";
    }

    /**
     * Returns the property {@code systemLanguage}.
     * @return the property {@code systemLanguag}
     */
    @JsxGetter(@WebBrowser(IE))
    public String getSystemLanguage() {
        return getBrowserVersion().getSystemLanguage();
    }

    /**
     * Returns the property {@code userAgent}.
     * @return the property {@code userAgent}
     */
    @JsxGetter
    public String getUserAgent() {
        return getBrowserVersion().getUserAgent();
    }

    /**
     * Returns the property {@code userLanguage}.
     * @return the property {@code userLanguage}
     */
    @JsxGetter(@WebBrowser(IE))
    public String getUserLanguage() {
        return getBrowserVersion().getUserLanguage();
    }

    /**
     * Returns an empty array because HtmlUnit does not support embedded objects.
     * @return an empty array
     */
    @JsxGetter
    public Object getPlugins() {
        initPlugins();
        return plugins_;
    }

    private void initPlugins() {
        if (plugins_ != null) {
            return;
        }
        plugins_ = new PluginArray();
        plugins_.setParentScope(this);
        plugins_.setPrototype(getPrototype(PluginArray.class));

        mimeTypes_ = new MimeTypeArray();
        mimeTypes_.setParentScope(this);
        mimeTypes_.setPrototype(getPrototype(MimeTypeArray.class));

        for (final PluginConfiguration pluginConfig : getBrowserVersion().getPlugins()) {
            final Plugin plugin = new Plugin(pluginConfig.getName(), pluginConfig.getDescription(),
                    pluginConfig.getVersion(), pluginConfig.getFilename());
            plugin.setParentScope(this);
            plugin.setPrototype(getPrototype(Plugin.class));
            plugins_.add(plugin);

            for (final PluginConfiguration.MimeType mimeTypeConfig : pluginConfig.getMimeTypes()) {
                final MimeType mimeType = new MimeType(mimeTypeConfig.getType(), mimeTypeConfig.getDescription(),
                    mimeTypeConfig.getSuffixes(), plugin);
                mimeType.setParentScope(this);
                mimeType.setPrototype(getPrototype(MimeType.class));
                mimeTypes_.add(mimeType);
                plugin.add(mimeType);
            }
        }
    }

    /**
     * Returns the {@code mimeTypes} property.
     * @return the {@code mimeTypes} property
     */
    @JsxGetter
    public Object getMimeTypes() {
        initPlugins();
        return mimeTypes_;
    }

    /**
     * Indicates if Java is enabled.
     * @return true/false (see {@link com.gargoylesoftware.htmlunit.WebClientOptions#isAppletEnabled()}
     */
    @JsxFunction
    public boolean javaEnabled() {
        return getWindow().getWebWindow().getWebClient().getOptions().isAppletEnabled();
    }

    /**
     * Returns {@code false} always as data tainting support is not enabled in HtmlUnit.
     * @return false
     */
    @JsxFunction({@WebBrowser(FF), @WebBrowser(IE)})
    public boolean taintEnabled() {
        return false;
    }

    /**
     * Returns the {@code geolocation} property.
     * @return the {@code geolocation} property
     */
    @JsxGetter
    public Geolocation getGeolocation() {
        final Geolocation geolocation = new Geolocation();
        geolocation.setPrototype(getPrototype(geolocation.getClass()));
        geolocation.setParentScope(getParentScope());
        return geolocation;
    }

    /**
     * Returns the {@code buildID} property.
     * @return the {@code buildID} property
     */
    @JsxGetter(@WebBrowser(FF))
    public String getBuildID() {
        return getBrowserVersion().getBuildId();
    }

    /**
     * Returns the {@code vendor} property.
     * @return the {@code vendor} property
     */
    @JsxGetter
    public String getVendor() {
        return getBrowserVersion().getVendor();
    }

    /**
     * Returns the {@code vendorSub} property.
     * @return the {@code vendorSub} property
     */
    @JsxGetter({@WebBrowser(FF), @WebBrowser(CHROME), @WebBrowser(EDGE)})
    public String getVendorSub() {
        return "";
    }

    /**
     * Returns the {@code doNotTrack} property.
     * @return the {@code doNotTrack} property
     */
    @JsxGetter(@WebBrowser(FF))
    public String getDoNotTrack() {
        if (getWindow().getWebWindow().getWebClient().getOptions().isDoNotTrackEnabled()) {
            return "yes";
        }
        return "unspecified";
    }

    /**
     * Returns the {@code oscpu} property.
     * @return the {@code oscpu} property
     */
    @JsxGetter(@WebBrowser(FF))
    public String getOscpu() {
        return "Windows NT 6.1";
    }
}
