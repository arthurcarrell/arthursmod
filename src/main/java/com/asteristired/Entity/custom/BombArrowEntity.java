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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BombArrowEntity extends PersistentProjectileEntity {
    public BombArrowEntity(EntityType<? extends BombArrowEntity> type, World world) {
        super(type, world);
    }

    public BombArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.BOMB_ARROW, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.BOMB_ARROW);
    }

    private void DoExplode() {
        if (!this.getWorld().isClient()) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.5f, World.ExplosionSourceType.NONE);
            this.discard();
        }
    }
    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        DoExplode();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        DoExplode();
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        Vec3d position = this.getPos();
        if (!world.isClient() && !inGround) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticles(ParticleTypes.FLAME, position.x, position.y, position.z, 1, 0,0,0, 0);
        }
        super.tick();
    }
}
