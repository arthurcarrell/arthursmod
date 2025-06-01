package com.asteristired.StatusEffect;

import com.asteristired.DamageType.ModDamageTypes;
import com.asteristired.Interface.FrozenTrackerAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class ShockEffect extends StatusEffect {
    public ShockEffect(StatusEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                "11111111-2222-3333-4444-555555555556",
                -1.0,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    private void dropItem(LivingEntity entity, ItemStack itemStack, Hand hand) {
        entity.dropStack(itemStack.copy(), 0.5F);
        entity.setStackInHand(hand, ItemStack.EMPTY);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof MobEntity mob) {
            mob.setAiDisabled(true);
        }
        ItemStack mainhandItem = entity.getMainHandStack();
        ItemStack offhandItem = entity.getOffHandStack();
        if (!mainhandItem.isEmpty()) { dropItem(entity, mainhandItem, Hand.MAIN_HAND); }
        if (!offhandItem.isEmpty()) { dropItem(entity, offhandItem, Hand.OFF_HAND); }

        DamageSource damageSource = entity.getDamageSources().create(ModDamageTypes.SHOCK, entity.getAttacker());
        entity.damage(damageSource, 5);

        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof MobEntity mob) {
            mob.setAiDisabled(false);
        }
        super.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) entity.getWorld();
            Vec3d position = entity.getPos();
            serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, position.x, position.y+1, position.z, 20, 0.5,0.5,0.5, 0.1);
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
