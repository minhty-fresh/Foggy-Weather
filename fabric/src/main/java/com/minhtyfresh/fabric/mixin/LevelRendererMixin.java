package com.minhtyfresh.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.x;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow @Nullable private ClientLevel level;

    @Redirect(
            method = "renderLevel",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V"
            ))
    private void injected(LevelRenderer instance,
                          PoseStack poseStack,
                          Matrix4f projectionMatrix,
                          float partialTick,
                          Camera camera,
                          boolean isFoggy,
                          Runnable skyFogSetup) {
        ClientLevel level = this.level;

    }

//    @ModifyArgs(
//            method = "renderLevel",
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V"))
//    private void injected(Args args, @Local LevelRenderer levelRenderer) {
//        Runnable skyFogSetup = args.get(5);
//        args.set(5, () -> FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, f, flag1, partialTick));
    }
}
