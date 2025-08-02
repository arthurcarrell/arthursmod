package com.asteristired.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.asteristired.ArthursMod.LOGGER;

@Mixin(LivingEntity.class)
public abstract class LivingEntityAirVelocityMixin extends Entity {

    @Shadow public abstract float getYaw(float tickDelta);

    @Shadow public abstract boolean isClimbing();

    @Shadow public abstract boolean isFallFlying();

    @Shadow @Final private static Logger LOGGER;
    @Unique
    private boolean overSpeed = false;
    @Unique
    private final float ACCELERATION = 0.02f;
    @Unique
    private int airTime = 0;
    private final int AIRTIME_NEEDED = 10;
    private final Boolean isEnabled = true;
    @Unique
    private boolean hasPlayedWindSound = false;

    public LivingEntityAirVelocityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    public void EnableOverSpeed() {
        overSpeed = true;
    }

    @Inject(method = "travel", at = @At("TAIL"), cancellable=true)
    public void modifyAirMovement(Vec3d movementInput, CallbackInfo callbackInfo) {
        if (isEnabled) {
            if (!this.isOnGround() && !this.isClimbing() && !this.touchingWater && !this.inPowderSnow && !this.isFallFlying() && !this.isSpectator() && this.isPlayer()) {
                airTime++;
                if (airTime >= AIRTIME_NEEDED) {
                    overSpeed = true;
                }
            } else {
                airTime = 0;
                overSpeed = false;
                hasPlayedWindSound = false;
            }

            if (!this.isOnGround() && overSpeed) {
                Vec3d velocity = this.getVelocity();

                double yaw = Math.toRadians(this.getYaw());

                double rotateX = movementInput.x * Math.cos(yaw) - movementInput.z * Math.sin(yaw);
                double rotateZ = movementInput.x * Math.sin(yaw) + movementInput.z * Math.cos(yaw);

                // calculate the final speed
                float finalSpeed = ACCELERATION * ((float) airTime / 30);
                if (finalSpeed > 0.2f) {
                    finalSpeed = 0.2f;
                }

                Vec3d multipliedVelocity = new Vec3d(rotateX, 0, rotateZ).normalize().multiply(finalSpeed);
                this.setVelocity(velocity.add(multipliedVelocity));
                this.velocityDirty = true; // let the game know that ive modified the velocity.
            }
        }
    }
}
