package com.asteristired.Entity.custom;

import com.asteristired.Entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class OreBulletEntity extends PersistentProjectileEntity {
    public OreBulletEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public OreBulletEntity(World world, PlayerEntity owner) {
        super(ModEntities.ORE_BULLET, owner, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > 100) this.discard();
        if (!this.getWorld().isClient()) {
            ServerWorld world = (ServerWorld) this.getWorld();
            world.spawnParticles(new DustParticleEffect(new Vector3f(1.0f,0.0f,0.0f), 1.0f), this.getX(), this.getY(), this.getZ(), 10, 0.05, 0.05, 0.05, 0);
        }

    }

    @Override
    protected ItemStack asItemStack() {
        return Items.AIR.getDefaultStack();
    }

    @Override
    public boolean shouldRender(double distance) {
        return false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.discard();
    }
}
