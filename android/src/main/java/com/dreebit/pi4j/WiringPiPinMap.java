package com.dreebit.pi4j;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.util.HashMap;

class WiringPiPinMap {

    static HashMap<String, Pin> getPinMap() {
        return new HashMap<String, Pin>() {
            {
                put("GPIO 0", RaspiPin.GPIO_00);
                put("GPIO 1", RaspiPin.GPIO_01);
                put("GPIO 2", RaspiPin.GPIO_02);
                put("GPIO 3", RaspiPin.GPIO_03);
                put("GPIO 4", RaspiPin.GPIO_04);
                put("GPIO 5", RaspiPin.GPIO_05);
                put("GPIO 6", RaspiPin.GPIO_06);
                put("GPIO 7", RaspiPin.GPIO_07);
                put("GPIO 8", RaspiPin.GPIO_08);
                put("GPIO 9", RaspiPin.GPIO_09);
                put("GPIO 10", RaspiPin.GPIO_10);
                put("GPIO 11", RaspiPin.GPIO_11);
                put("GPIO 12", RaspiPin.GPIO_12);
                put("GPIO 13", RaspiPin.GPIO_13);
                put("GPIO 14", RaspiPin.GPIO_14);
                put("GPIO 15", RaspiPin.GPIO_15);
                put("GPIO 16", RaspiPin.GPIO_16);
                put("GPIO 17", RaspiPin.GPIO_17);
                put("GPIO 18", RaspiPin.GPIO_18);
                put("GPIO 19", RaspiPin.GPIO_19);
                put("GPIO 20", RaspiPin.GPIO_20);
                put("GPIO 21", RaspiPin.GPIO_21);
                put("GPIO 22", RaspiPin.GPIO_22);
                put("GPIO 23", RaspiPin.GPIO_23);
                put("GPIO 24", RaspiPin.GPIO_24);
                put("GPIO 25", RaspiPin.GPIO_25);
                put("GPIO 26", RaspiPin.GPIO_26);
                put("GPIO 27", RaspiPin.GPIO_27);
                put("GPIO 28", RaspiPin.GPIO_28);
                put("GPIO 29", RaspiPin.GPIO_29);
                put("GPIO 30", RaspiPin.GPIO_30);
                put("GPIO 31", RaspiPin.GPIO_31);
            }};
    }

}
