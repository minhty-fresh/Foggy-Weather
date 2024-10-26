package com.minhtyfresh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FoggyWeather {
    /* - Identifiers */

    /**
     * This is the mod's unique identifier. This should never change. If a change is required, then it is important that
     * mod developers using our API are properly informed of the change.
     */
    public static final String MOD_ID = "foggy_weather";

    /**
     * This is the mod's display name. This can change, but should not be required since it closely resembles the mod's
     * unique identifier.
     */
    public static final String MOD_NAME = "Foggy Weather";

    /**
     * This is a unique logger instance. It will change the output visible in the debugging console and in a player's
     * runtime console.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
}
