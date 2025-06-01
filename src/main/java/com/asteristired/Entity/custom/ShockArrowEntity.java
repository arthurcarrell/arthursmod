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

public class ShockArrowEntity extends PersistentProjectileEntity {
    public ShockArrowEntity(EntityType<? extends ShockArrowEntity> type, World world) {
        super(type, world);
    }

    public ShockArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.SHOCK_ARROW, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.SHOCK_ARROW);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (!target.hasStatusEffect(ModStatusEffects.CC_IMMUNE)) {
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.SHOCK_EFFECT, 40, 0, true, true, true)); // freeze thje entity for 5 seconds
            target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CC_IMMUNE, 80, 0, true, false, false)); // make the enemy immune to cc for 10 seconds
        }
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        Vec3d position = this.getPos();
        if (!world.isClient() && !inGround) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, position.x, position.y, position.z, 10, 0.1,0.1,0.1, 0.1);
        }
        super.tick();
    }
}
