package com.asteristired.Entity.custom;

import com.asteristired.Entity.ModEntities;
import com.asteristired.Item.ModItems;
import com.asteristired.StatusEffect.ModStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class PyroBombEntity extends ThrownItemEntity {
    public PyroBombEntity(EntityType<? extends PyroBombEntity> type, World world) {
        super(type, world);
    }

    public PyroBombEntity(World world, LivingEntity owner) {
        super(ModEntities.PYROBOMB, owner, world);
    }

    public PyroBombEntity(World world, double x, double y, double z) {
        super(ModEntities.PYROBOMB, x, y, z, world);
    }



    @Override
    protected Item getDefaultItem() {
        return ModItems.PYROBOMB;
    }

    protected void onHit() {

        int explosionRadius = 3;
        int fireRadius = 4;
        World world = this.getWorld();
        if (!world.isClient) {

            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d position = this.getPos();

            // create the explosion and the particle effects.
            this.getWorld().createExplosion(null, position.getX(), position.getY(), position.getZ(), explosionRadius, World.ExplosionSourceType.TNT);
            serverWorld.spawnParticles(ParticleTypes.FLAME, position.x, position.y + 1, position.z, 30, 2, 2, 2, 0.02);

            // loop through each block and convert it to a cooler one if possible.
            for (BlockPos pos : BlockPos.iterateOutwards(this.getBlockPos(), explosionRadius, explosionRadius, explosionRadius)) {
                BlockState blockState = world.getBlockState(pos);

                if (blockState.isOf(Blocks.WATER)) { world.setBlockState(pos, Blocks.AIR.getDefaultState());}
                if (blockState.isOf(Blocks.ICE)) { world.setBlockState(pos, Blocks.WATER.getDefaultState());}

                if (blockState.isAir() && world.getBlockState(pos.down()).isOpaque() && random.nextBoolean()) {
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
            }
            // get a list of entities caught in explosion
            Vec3d cornerA = new Vec3d(position.x - fireRadius, position.y - fireRadius, position.z - fireRadius);
            Vec3d cornerB = new Vec3d(position.x + fireRadius, position.y + fireRadius, position.z + fireRadius);
            List<LivingEntity> entityList =  world.getEntitiesByClass(LivingEntity.class, new Box(cornerA, cornerB), livingEntity -> isAlive() && !isSpectator());

            // loop through each entity and set them on fire
            for (LivingEntity target : entityList) {
                target.setOnFire(true);
            }



            // destroy this entity
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
