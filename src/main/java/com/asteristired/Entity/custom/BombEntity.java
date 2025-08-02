package com.asteristired.Entity.custom;

import com.asteristired.Entity.ModEntities;
import com.asteristired.Item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BombEntity extends ThrownItemEntity {
    public BombEntity(EntityType<? extends BombEntity> type, World world) {
        super(type, world);
    }

    public BombEntity(World world, LivingEntity owner) {
        super(ModEntities.BOMB, owner, world);
    }

    public BombEntity(World world, double x, double y, double z) {
        super(ModEntities.BOMB, x, y, z, world);
    }



    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB;
    }


    protected void onHit() {
        if (!this.getWorld().isClient) {
            Vec3d position = this.getPos();
            this.getWorld().createExplosion(null, position.getX(), position.getY(), position.getZ(), 2, World.ExplosionSourceType.TNT);
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        onHit();
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        onHit();
        super.onEntityHit(entityHitResult);
    }
}
