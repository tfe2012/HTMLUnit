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

import com.gargoylesoftware.htmlunit.javascript.FunctionWrapper;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxStaticFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

import net.sourceforge.htmlunit.corejs.javascript.Context;
import net.sourceforge.htmlunit.corejs.javascript.Function;
import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
import net.sourceforge.htmlunit.corejs.javascript.NativeArray;
import net.sourceforge.htmlunit.corejs.javascript.NativeObject;
import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
import net.sourceforge.htmlunit.corejs.javascript.TopLevel;
import net.sourceforge.htmlunit.corejs.javascript.Undefined;

/**
 * A JavaScript object for {@code Promise}.
 *
 * @author Ahmed Ashour
 * @author Marc Guillemot
 */
@JsxClass(browsers = {@WebBrowser(CHROME), @WebBrowser(FF), @WebBrowser(EDGE)})
public class Promise extends SimpleScriptable {

    private Object value_;
    /** To be set only by {@link #all(Context, Scriptable, Object[], Function)}. */
    private Promise[] all_;
    private boolean resolve_ = true;
    private String exceptionDetails_;

    /**
     * Default constructor.
     */
    public Promise() {
    }

    /**
     * Facility constructor.
     * @param window the owning window
     */
    public Promise(final Window window) {
        setParentScope(window);
        setPrototype(window.getPrototype(Promise.class));
    }

    /**
     * Constructor new promise with the given {@code object}.
     *
     * @param object the object
     */
    @JsxConstructor
    public Promise(final Object object) {
        if (object instanceof Promise) {
            value_ = ((Promise) object).value_;
        }
        else if (object instanceof NativeObject) {
            final NativeObject nativeObject = (NativeObject) object;
            value_ = nativeObject.get("then", nativeObject);
        }
        else {
            value_ = object;
        }
    }

    /**
     * Returns a {@link Promise} object that is resolved with the given value.
     *
     * @param context the context
     * @param thisObj this object
     * @param args the arguments
     * @param function the function
     * @return a {@link Promise}
     */
    @JsxStaticFunction
    public static Promise resolve(final Context context, final Scriptable thisObj, final Object[] args,
            final Function function) {
        final Promise promise = new Promise(args.length != 0 ? args[0] : Undefined.instance);
        promise.setResolve(true);
        promise.setParentScope(thisObj.getParentScope());
        promise.setPrototype(getWindow(thisObj).getPrototype(promise.getClass()));
        return promise;
    }

    /**
     * Returns a {@link Promise} object that is rejected with the given value.
     *
     * @param context the context
     * @param thisObj this object
     * @param args the arguments
     * @param function the function
     * @return a {@link Promise}
     */
    @JsxStaticFunction
    public static Promise reject(final Context context, final Scriptable thisObj, final Object[] args,
            final Function function) {
        final Promise promise = new Promise(args.length != 0 ? args[0] : Undefined.instance);
        promise.setResolve(false);
        promise.setParentScope(thisObj.getParentScope());
        promise.setPrototype(getWindow(thisObj).getPrototype(promise.getClass()));
        return promise;
    }

    private void setResolve(final boolean resolve) {
        resolve_ = resolve;
    }

    /**
     * Also sets the value of this promise.
     */
    private boolean isResolved(final Function onRejected) {
        if (all_ != null) {
            final Object[] values = new Object[all_.length];
            for (int i = 0; i < all_.length; i++) {
                final Promise p = all_[i];
                if (!p.isResolved(onRejected)) {
                    value_ = p.value_;
                    return false;
                }

                if (p.value_ instanceof Function) {
                    // TODO
                }
                else {
                    values[i] = p.value_;
                }
            }
            final NativeArray array = new NativeArray(values);
            ScriptRuntime.setBuiltinProtoAndParent(array, getParentScope(), TopLevel.Builtins.Array);
            value_ = array;
        }
        return resolve_;
    }

    /**
     * Returns a {@link Promise} that resolves when all of the promises in the iterable argument have resolved,
     * or rejects with the reason of the first passed promise that rejects.
     *
     * @param context the context
     * @param thisObj this object
     * @param args the arguments
     * @param function the function
     * @return a {@link Promise}
     */
    @JsxStaticFunction
    public static Promise all(final Context context, final Scriptable thisObj, final Object[] args,
            final Function function) {
        final Promise promise = new Promise();
        promise.setResolve(true);
        if (args.length == 0) {
            promise.all_ = new Promise[0];
        }
        else {
            final NativeArray array = (NativeArray) args[0];
            final int length = (int) array.getLength();
            promise.all_ = new Promise[length];
            for (int i = 0; i < length; i++) {
                final Object o = array.get(i);
                if (o instanceof Promise) {
                    promise.all_[i] = (Promise) o;
                }
                else {
                    promise.all_[i] = resolve(null, thisObj, new Object[] {o}, null);
                }
            }
        }
        promise.setParentScope(thisObj.getParentScope());
        promise.setPrototype(getWindow(thisObj).getPrototype(promise.getClass()));
        return promise;
    }

    /**
     * It takes two arguments, both are callback functions for the success and failure cases of the Promise.
     *
     * @param onFulfilled success function
     * @param onRejected failure function
     * @return {@link Promise}
     */
    @JsxFunction
    public Promise then(final Function onFulfilled, final Function onRejected) {
        final Window window = getWindow();
        final Promise promise = new Promise(window);
        final Promise thisPromise = this;

        PostponedAction thenAction = new PostponedAction(window.getDocument().getPage(), "Promise.then") {

            @Override
            public void execute() throws Exception {
                Context.enter();
                try {
                    Object newValue = null;
                    final Function toExecute = isResolved(onRejected) ? onFulfilled : onRejected;
                    if (value_ instanceof Function) {
                        final WasCalledFunctionWrapper wrapper = new WasCalledFunctionWrapper(toExecute);
                        try {
                            ((Function) value_).call(Context.getCurrentContext(), window, thisPromise,
                                    new Object[] {wrapper, onRejected});
                            if (wrapper.wasCalled_) {
                                newValue = wrapper.value_;
                            }
                        }
                        catch (final JavaScriptException e) {
                            if (onRejected == null) {
                                promise.exceptionDetails_ = e.details();
                            }
                            else if (!wrapper.wasCalled_) {
                                newValue = onRejected.call(Context.getCurrentContext(), window, thisPromise,
                                        new Object[] {e.getValue()});
                            }
                        }
                    }
                    else {
                        newValue = toExecute.call(Context.getCurrentContext(), window, thisPromise,
                                new Object[] {value_});
                    }
                    promise.value_ = newValue;
                }
                finally {
                    Context.exit();
                }
            }
        };

        final JavaScriptEngine jsEngine = window.getWebWindow().getWebClient().getJavaScriptEngine();
        jsEngine.addPostponedAction(thenAction);

        return promise;
    }

    /**
     * Returns a Promise and deals with rejected cases only.
     *
     * @param onRejected failure function
     * @return {@link Promise}
     */
    @JsxFunction(functionName = "catch")
    public Promise catch_js(final Function onRejected) {
        final Window window = getWindow();
        final Promise promise = new Promise(window);
        final Promise thisPromise = this;

        final PostponedAction thenAction = new PostponedAction(window.getDocument().getPage(), "Promise.catch") {

            @Override
            public void execute() throws Exception {
                Context.enter();
                try {
                    final Object newValue = onRejected.call(Context.getCurrentContext(), window, thisPromise,
                            new Object[] {exceptionDetails_});
                    promise.value_ = newValue;
                }
                finally {
                    Context.exit();
                }
            }
        };

        final JavaScriptEngine jsEngine = window.getWebWindow().getWebClient().getJavaScriptEngine();
        jsEngine.addPostponedAction(thenAction);

        return promise;
    }

    private static class WasCalledFunctionWrapper extends FunctionWrapper {
        private boolean wasCalled_;
        private Object value_;

        WasCalledFunctionWrapper(final Function wrapped) {
            super(wrapped);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object call(final Context cx, final Scriptable scope, final Scriptable thisObj, final Object[] args) {
            wasCalled_ = true;
            value_ = super.call(cx, scope, thisObj, args);
            return value_;
        }
    }
}
