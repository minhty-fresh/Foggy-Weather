package com.minhtyfresh.fabric.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements LevelInvoker, LevelTimeAccessInvoker{
    @Inject(
            method = "getSkyDarken",
            at = @At("HEAD"),
            cancellable = true
    )
    // This affects only the internal sky level
    // https://minecraft.wiki/w/Light#Internal_light_level
    public void getSkyDarken(float g, CallbackInfoReturnable<Float> cir) {
        float RAIN_LIGHT_DAMPEN_LEVEL = 10.0f; // default if 5.0f, possible range is 0->16
        float THUNDER_LIGHT_DAMPEN_LEVEL = 10.0f; // default is 5.0f, possible range is 0->16

        float timeOfDay = this.invokeGetTimeOfDay(g);
        float skyDarknessPercentageToAdd = 1.0f - (Mth.cos(timeOfDay * ((float)Math.PI * 2)) * 2.0f + 0.2f);
        // TODO add config option to enable brighter nights during full moon and 3/4 moon
        float maximumSkyDarkenPercentage = switch (this.invokeGetMoonPhase()) {
            case (0) -> // full moon
                    14.0f / 16.0f;
            case (1), (7) -> // 3/4 full moon
                    15.0f / 16.0f;
            default -> 1.0f;
        };
        skyDarknessPercentageToAdd = Mth.clamp(skyDarknessPercentageToAdd, 0, maximumSkyDarkenPercentage);
        float darkenPercentage = 1.0f - skyDarknessPercentageToAdd;
        darkenPercentage *= 1.0f - this.invokeGetRainLevel(g) * RAIN_LIGHT_DAMPEN_LEVEL / 16.0f;
        darkenPercentage = darkenPercentage * (1.0f - this.invokeGetThunderLevel(g) * THUNDER_LIGHT_DAMPEN_LEVEL / 16.0f) * 0.8f + 0.2f;
        cir.setReturnValue(darkenPercentage);
    }
}
