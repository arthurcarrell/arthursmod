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
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;

public class CryoBombEntity extends ThrownItemEntity {
    public CryoBombEntity(EntityType<? extends CryoBombEntity> type, World world) {
        super(type, world);
    }

    public CryoBombEntity(World world, LivingEntity owner) {
        super(ModEntities.CRYOBOMB, owner, world);
    }

    public CryoBombEntity(World world, double x, double y, double z) {
        super(ModEntities.CRYOBOMB, x, y, z, world);
    }



    @Override
    protected Item getDefaultItem() {
        return ModItems.CRYOBOMB;
    }

    protected void onHit() {

        int explosionRadius = 2;
        int freezeRadius = 3;
        World world = this.getWorld();
        if (!world.isClient) {

            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d position = this.getPos();

            // loop through each block and convert it to a cooler one if possible.
            for (BlockPos pos : BlockPos.iterateOutwards(this.getBlockPos(), explosionRadius, explosionRadius, explosionRadius)) {
                BlockState blockState = world.getBlockState(pos);

                if (blockState.isOf(Blocks.WATER)) { world.setBlockState(pos, Blocks.FROSTED_ICE.getDefaultState());}
                if (blockState.isOf(Blocks.LAVA)) { world.setBlockState(pos, Blocks.BASALT.getDefaultState());}
            }

            // create the explosion and the particle effects.
            this.getWorld().createExplosion(null, position.getX(), position.getY(), position.getZ(), 2, World.ExplosionSourceType.TNT);
            serverWorld.spawnParticles(ParticleTypes.SNOWFLAKE, position.x, position.y + 1, position.z, 30, 2, 2, 2, 0.02);

            // get a list of entities caught in explosion
            Vec3d cornerA = new Vec3d(position.x - freezeRadius, position.y - freezeRadius, position.z - freezeRadius);
            Vec3d cornerB = new Vec3d(position.x + freezeRadius, position.y + freezeRadius, position.z + freezeRadius);
            List<LivingEntity> entityList =  world.getEntitiesByClass(LivingEntity.class, new Box(cornerA, cornerB), livingEntity -> isAlive() && !isSpectator());

            // loop through each entity and give them the freeze effect (if they arent CC immune)
            for (LivingEntity target : entityList) {
                if (!target.hasStatusEffect(ModStatusEffects.CC_IMMUNE)) {
                    target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.FROZEN_EFFECT, 100, 0, true, true, true)); // freeze thje entity for 5 seconds
                    target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CC_IMMUNE, 200, 0, true, false, false)); // make it so the entity cant be frozen
                }
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
