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
package com.gargoylesoftware.htmlunit.html.applets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.javascript.host.Window;

/**
 * <span style="color:red">INTERNAL API - SUBJECT TO CHANGE AT ANY TIME - USE AT YOUR OWN RISK.</span><br>
 * Class loader for loading applets.
 *
 * @author Marc Guillemot
 * @author Ronald Brill
 */
public class AppletClassLoader extends URLClassLoader {

    private static final Log LOG = LogFactory.getLog(AppletClassLoader.class);

    /**
     * The constructor.
     * @param window the window containing this applet
     */
    public AppletClassLoader(final Window window) {
        super(new URL[0]);

        try {
            loadOurNetscapeStuff("netscape.javascript.JSException");
            final Class<?> jsObjectClass = loadOurNetscapeStuff("netscape.javascript.JSObject");
            MethodUtils.invokeExactStaticMethod(jsObjectClass, "setWindow", window);
        }
        catch (final Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Adds the jar file to the classpath for the applet.
     * @param jarUrl the url of the jar file
     */
    public void addArchiveToClassPath(final URL jarUrl) {
        addURL(jarUrl);
    }

    /**
     * Adds the class defined by the WebResponse to the classpath for the applet.
     * @param className the name of the class to load
     * @param webResponse the web response
     * @throws IOException in case of problem working with the response content
     */
    public void addClassToClassPath(final String className, final WebResponse webResponse) throws IOException {
        final byte[] bytes = IOUtils.toByteArray(webResponse.getContentAsStream());
        defineClass(className, bytes, 0, bytes.length);
    }

    private Class<?> loadOurNetscapeStuff(final String classNane) throws IOException {
        final String myNetscapeClassName = classNane.replace('.',  '/') + ".class";

        final Enumeration<URL> locations = getClass().getClassLoader().getResources(myNetscapeClassName);
        final URL myLocation = getClass().getProtectionDomain().getCodeSource().getLocation();
        while (locations.hasMoreElements()) {
            final URL pos = locations.nextElement();
            if (pos.toExternalForm().contains(myLocation.toExternalForm())) {
                try (InputStream byteStream = pos.openStream()) {
                    final byte[] bytes = IOUtils.toByteArray(byteStream);
                    return defineClass(classNane, bytes, 0, bytes.length);
                }
            }
        }
        return null;
    }
}
