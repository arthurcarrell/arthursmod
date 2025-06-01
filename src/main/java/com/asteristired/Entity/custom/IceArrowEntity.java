package com.asteristired.Entity.custom;

import com.asteristired.Entity.ModEntities;
import com.asteristired.Item.ModItems;
import com.asteristired.StatusEffect.ModStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceArrowEntity extends PersistentProjectileEntity {
    public IceArrowEntity(EntityType<? extends IceArrowEntity> type, World world) {
        super(type, world);
    }

    public IceArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.ICE_ARROW, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.ICE_ARROW);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (!target.hasStatusEffect(ModStatusEffects.CC_IMMUNE)) {
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.FROZEN_EFFECT, 100, 0, true, true, true)); // freeze thje entity for 5 seconds
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CC_IMMUNE, 200, 0, true, false, false)); // freeze thje entity for 5 seconds
        }
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        Vec3d position = this.getPos();
        if (!world.isClient() && !inGround) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ParticleTypes.SNOWFLAKE, position.x, position.y, position.z, 1, 0,0,0, 0);
        }
        super.tick();
    }
}
