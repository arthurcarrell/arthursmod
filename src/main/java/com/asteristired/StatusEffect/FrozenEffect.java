package com.asteristired.StatusEffect;

import com.asteristired.Interface.FrozenTrackerAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static com.asteristired.ArthursMod.LOGGER;

public class FrozenEffect extends StatusEffect {
    public FrozenEffect(StatusEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                "11111111-2222-3333-4444-555555555555",
                -1.0,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof MobEntity mob) {
            mob.setAiDisabled(true);
        }
        ((FrozenTrackerAccessor) entity).setFreeze(true);
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof MobEntity mob) {
            mob.setAiDisabled(false);
        }
        if (!entity.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) entity.getWorld();
            serverWorld.playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS);
        }
        ((FrozenTrackerAccessor) entity).setFreeze(false);
        super.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) entity.getWorld();
            Vec3d position = entity.getPos();
            serverWorld.spawnParticles(ParticleTypes.SNOWFLAKE, position.x, position.y + 1, position.z, 5, 0.2, 0.2, 0.2, 0.02);
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
