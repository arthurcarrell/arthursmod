package com.asteristired.Entity.custom;

import com.asteristired.Sounds.ModSounds;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EyefishEntity extends FishEntity {

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    public EyefishEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
    }

    private void setupAnimationStates() {
        if (!this.isSubmergedInWater()) {
            this.swimAnimationState.stop();
            this.flopAnimationState.start(age);
        } else {
            this.flopAnimationState.stop();
            this.swimAnimationState.start(age);
        }
    }

    public static DefaultAttributeContainer.Builder CreateEyefishAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.getWorld().addParticle(ParticleTypes.GLOW, this.getParticleX(0.6), this.getRandomBodyY(), this.getParticleZ(0.6), 0.0, 0.0, 0.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(4, new SwimAroundGoal(this, 5.0, 40));
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    @Override
    public ItemStack getBucketItem() {
        return Items.COD_BUCKET.getDefaultStack();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }
}
