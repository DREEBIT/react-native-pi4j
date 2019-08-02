package com.dreebit.pi4j;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.util.HashMap;
import java.util.Map;

public class GpioManager {

    private static volatile GpioManager sInstance = new GpioManager();
    private ReactApplicationContext reactContext;
    private GpioController gpioController;
    private Map<String, Pin> pinMap;
    private Map<String, GpioPinDigitalInput> gpioButtons;

    // Throttle map
    private HashMap<String, Throttle> gpioThrottles;

    //private constructor.
    private GpioManager() {
        this.gpioController = GpioFactory.getInstance();
        this.pinMap = WiringPiPinMap.getPinMap();
        this.gpioButtons = new HashMap<>();
        gpioThrottles = new HashMap<>();
    }

    static GpioManager getInstance() {
        return sInstance;
    }

    public ReactApplicationContext getReactContext() {
        return reactContext;
    }

    void setReactContext(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    void subscribeGpio(String gpioName) {
        if (pinMap.containsKey(gpioName) && !gpioButtons.containsKey(gpioName)) {
            GpioPinDigitalInput gpioButton = gpioController.provisionDigitalInputPin(pinMap.get(gpioName));
            //gpioButton.setDebounce(500);
            gpioButton.addListener(gpioCallback);
            gpioButton.setShutdownOptions(true);
            gpioButtons.put(gpioName, gpioButton);
        }
    }

    void unsubscribe(String gpioName) {
        if (gpioButtons.containsKey(gpioName)) {
            GpioPinDigitalInput gpioButton = gpioButtons.get(gpioName);
            gpioButton.removeAllListeners();
            gpioButtons.remove(gpioName);
        }
    }

    private GpioPinListenerDigital gpioCallback = new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            PinState state = event.getState();
            GpioPin pin = event.getPin();

            // ensure throttling
            String throttleKey = pin.getName() + (state == PinState.HIGH ? "_1" : "_0");
            if (!gpioThrottles.containsKey(throttleKey)) {
                gpioThrottles.put(throttleKey, new Throttle(500));
            }

            WritableMap params = Arguments.createMap();
            params.putInt(pin.getName(), state.getValue());
            sendEvent(gpioThrottles.get(throttleKey), GpioManager.this.reactContext, "RNPinState_Changed", params);
        }
    };

    private void sendEvent(final Throttle throttle,
                           final ReactContext reactContext,
                           final String eventName,
                           final @Nullable WritableMap params) {
        throttle.attempt(new Runnable() {
            @Override
            public void run() {
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(eventName, params);
            }
        });
    }

    void shutdown() {
        gpioController.shutdown();
    }

}
