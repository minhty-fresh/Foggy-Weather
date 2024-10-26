package com.minhtyfresh.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.minhtyfresh.FoggyWeather;

@Mod(FoggyWeather.MOD_ID)
public final class FoggyWeatherForge {
    public FoggyWeatherForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(FoggyWeather.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
//        FoggyWeather.init();
    }
}
