package com.dreebit.pi4j;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNPi4jModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNPi4jModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        GpioManager.getInstance().setReactContext(reactContext);
    }

    @Override
    public String getName() {
        return "RNPi4j";
    }

    @ReactMethod
    public void subscribeGpio(String gpioName) {
        GpioManager.getInstance().subscribeGpio(gpioName);
    }

    @ReactMethod
    public void unsubscribeGpio(String gpioName) {
        GpioManager.getInstance().unsubscribe(gpioName);
    }

    @ReactMethod
    public void shutdown() {
        GpioManager.getInstance().shutdown();
    }

}