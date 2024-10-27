package com.minhtyfresh.fabric.mixin;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
//    @Inject(
//            method = "advanceWeatherCycle",
//            at = @At("HEAD"),
//            cancellable = true
//    )
    /**
     * @author minhty-fresh
     * @reason Expanding the weather system
     */
    @Overwrite
    public void advanceWeatherCycle() {
        //ci.cancel();
        boolean actuallyIsRaining = this.isRaining(); // if rainLevel > 0.2
        if (this.dimensionType().hasSkyLight()) {
            if (this.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                int clearWeatherTime = this.serverLevelData.getClearWeatherTime(); // number of ticks until "clear weather" is ended
                int thunderTime = this.serverLevelData.getThunderTime(); // number of ticks until isThundering is toggled
                int rainTime = this.serverLevelData.getRainTime(); // number of ticks until isRaining is toggled
                boolean isThundering = this.levelData.isThundering(); // if raining is true, and this is true, then weather is Thundering
                boolean isRaining = this.levelData.isRaining(); // if it is currently raining
                if (clearWeatherTime > 0) {
                    --clearWeatherTime;
                    thunderTime = isThundering ? 0 : 1;
                    rainTime = isRaining ? 0 : 1;
                    isThundering = false;
                    isRaining = false;
                } else {
                    if (thunderTime > 0) {
                        if (--thunderTime == 0) {
                            isThundering = !isThundering;
                        }
                    } else {
                        thunderTime = isThundering ? THUNDER_DURATION.sample(this.random) : THUNDER_DELAY.sample(this.random);
                    }
                    if (rainTime > 0) {
                        if (--rainTime == 0) {
                            isRaining = !isRaining;
                        }
                    } else {
                        rainTime = isRaining ? RAIN_DURATION.sample(this.random) : RAIN_DELAY.sample(this.random);
                    }
                }
                this.serverLevelData.setThunderTime(thunderTime);
                this.serverLevelData.setRainTime(rainTime);
                this.serverLevelData.setClearWeatherTime(clearWeatherTime);
                this.serverLevelData.setThundering(isThundering);
                this.serverLevelData.setRaining(isRaining);
            }
            this.oThunderLevel = this.thunderLevel;
            this.thunderLevel = this.levelData.isThundering() ? (this.thunderLevel += 0.01f) : (this.thunderLevel -= 0.01f);
            this.thunderLevel = Mth.clamp(this.thunderLevel, 0.0f, 1.0f);
            this.oRainLevel = this.rainLevel;
            this.rainLevel = this.levelData.isRaining() ? (this.rainLevel += 0.01f) : (this.rainLevel -= 0.01f);
            this.rainLevel = Mth.clamp(this.rainLevel, 0.0f, 1.0f);
        }
        if (this.oRainLevel != this.rainLevel) {
            this.server.getPlayerList().broadcastAll((Packet<?>)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), this.dimension());
        }
        if (this.oThunderLevel != this.thunderLevel) {
            this.server.getPlayerList().broadcastAll((Packet<?>)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), this.dimension());
        }
        if (actuallyIsRaining != this.isRaining()) {
            if (actuallyIsRaining) {
                this.server.getPlayerList().broadcastAll((Packet<?>)new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0.0f), this.dimension());
            } else {
                this.server.getPlayerList().broadcastAll((Packet<?>)new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0f), this.dimension());
            }
            this.server.getPlayerList().broadcastAll((Packet<?>)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), this.dimension());
            this.server.getPlayerList().broadcastAll((Packet<?>)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), this.dimension());
        }
    }

}
