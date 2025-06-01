package com.asteristired.Entity.custom;

import com.asteristired.Entity.ModEntities;
import com.asteristired.Item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireArrowEntity extends PersistentProjectileEntity {
    public FireArrowEntity(EntityType<? extends FireArrowEntity> type, World world) {
        super(type, world);
    }

    public FireArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.FIRE_ARROW, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.FIRE_ARROW);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        target.setOnFireFor(3);
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
