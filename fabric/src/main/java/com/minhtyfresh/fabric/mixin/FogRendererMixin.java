package com.minhtyfresh.fabric.mixin;

import com.minhtyfresh.OverworldFogRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @Inject(
            method = "setupFog",
            at = @At("RETURN")
    )
    private static void nt_fabric_world_fog$onFinishSetup(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean shouldCreateFog, float partialTick, CallbackInfo callback)
    {
        // todo set up config to disable
        // OverworldFogRenderer.setupFog(camera, fogMode, RenderSystem::getShaderFogStart, RenderSystem::getShaderFogEnd, RenderSystem::setShaderFogShape, RenderSystem::setShaderFogStart, RenderSystem::setShaderFogEnd);
    }
}
