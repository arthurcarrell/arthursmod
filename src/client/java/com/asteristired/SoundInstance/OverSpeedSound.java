package com.asteristired.SoundInstance;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;

public class OverSpeedSound extends MovingSoundInstance {
    private final PlayerEntity player;
    private final float acceleration;
    private final float accelerationAmount;

    public OverSpeedSound(PlayerEntity player, float setAcceleration, float setAccelerationAmount) {
        super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS, Random.create());
        this.player = player;
        this.acceleration = setAcceleration;
        this.accelerationAmount = setAccelerationAmount;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1f;
    }

    @Override
    public boolean canPlay() {
        return super.canPlay();
    }

    @Override
    public void tick() {
        if (player.isRemoved() || acceleration < accelerationAmount) {
            this.setDone();
        } else {
            this.x = player.getX();
            this.y = player.getY();
            this.z = player.getZ();
        }
    }
}
