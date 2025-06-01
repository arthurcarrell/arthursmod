package com.asteristired.Item.ItemClasses;

import com.asteristired.DamageType.ModDamageTypes;
import com.asteristired.Enchantment.ModEnchantments;
import com.asteristired.Sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.asteristired.ArthursMod.LOGGER;
public class Mace extends AxeItem {
    public Mace(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    private void DoImpactParticles(ServerWorld world, LivingEntity target, LivingEntity attacker) {
        // exit if the target is not on solid ground
        if (!target.isOnGround()) return;

        BlockPos blockPos = target.getBlockPos().down(1);
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.isAir()) return;

        BlockStateParticleEffect particle = new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState);


        double xOffset = world.random.nextDouble();
        double yOffset = world.random.nextDouble();
        double zOffset = world.random.nextDouble();

        // finally spawn the particle
        world.spawnParticles(
                particle,
                blockPos.getX() + xOffset,
                blockPos.getY() + yOffset + 1,
                blockPos.getZ() + zOffset,
                300,
                2,
                0.1,
                2,
                0.1
        );
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        // get velocity
        double fallDistance = attacker.fallDistance;
        boolean isSmashAttack = attacker.fallDistance >= 2;
        boolean isHeavyHit = attacker.fallDistance > 6;
        int damage = 0;

        // does the player have slow falling? if so, it also cannot be a smash attack
        if (attacker.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
            isSmashAttack = false;
        }

        if (isSmashAttack) {
            // add 4 damage
            damage += 4;
            // play sound effect

            // particles
            if (!attacker.getWorld().isClient) {
                ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
                DoImpactParticles(serverWorld, target, attacker);
            }

            /*
            * for the first 3 blocks, each block increases damage by 4
            * for the next 5 blocks, each block increases damage by 2
            * every block after increases damage by 1
            */

            if (fallDistance - 3 <= 0) {
                damage += (int) (4 * fallDistance);
            } else if (fallDistance - 8 <= 0) {
                damage += 4 * 3;
                damage += (int) ((fallDistance - 3) * 2);
            } else {
                damage += 4 * 3;
                damage += 2 * 5;
                damage += (int) (fallDistance - 8);
            }

            // reset fall damage
            attacker.setVelocity(attacker.getVelocity().x, 0, attacker.getVelocity().z);
            attacker.fallDistance = 0;
            attacker.velocityModified = true;
        }

        if (isHeavyHit) {
            attacker.getWorld().playSound(null, target.getBlockPos(), ModSounds.MACE_HEAVY_SMASH_EVENT, SoundCategory.PLAYERS, 2.0f, 1.0f);
        } else if (isSmashAttack) {
            attacker.getWorld().playSound(null, target.getBlockPos(),ModSounds.MACE_SMASH_EVENT , SoundCategory.PLAYERS, 2.0f, 1.0f);
        }

        int knockUpLevel = EnchantmentHelper.getLevel(ModEnchantments.KNOCKUP, attacker.getMainHandStack());
        if (knockUpLevel > 0) {
            if (attacker.isOnGround() && isSmashAttack) {
                DamageSource damageSource = target.getDamageSources().create(ModDamageTypes.MACE_SMASH, attacker);
                target.damage(damageSource, damage);
            } else {
                target.addVelocity(new Vec3d(0, 1, 0));
                target.velocityModified = true;
            }

        }

        // apply damage
        if (damage > 0) {
            LOGGER.info(String.valueOf(damage));
            DamageSource damageSource = target.getDamageSources().create(ModDamageTypes.MACE_SMASH, attacker);
            target.damage(damageSource, damage);
        }

        // enchantment logic
        if (isSmashAttack) {
            int windBurstLevel = EnchantmentHelper.getLevel(ModEnchantments.WIND_BURST, attacker.getMainHandStack());

            if (windBurstLevel > 0) {
                attacker.addVelocity(new Vec3d(0, (1.15 + 0.35 * windBurstLevel), 0));
                attacker.velocityModified = true;
            }


        }

        return super.postHit(stack, target, attacker);
    }
}
