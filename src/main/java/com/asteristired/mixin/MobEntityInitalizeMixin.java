package com.asteristired.mixin;

import com.asteristired.Item.ModItems;
import com.asteristired.Tags.ModTags;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityInitalizeMixin {

    @Unique
    private void equipBiomeArrow(LivingEntity entity) {
        RegistryEntry<Biome> biomeEntry = entity.getWorld().getBiome(entity.getBlockPos());
        ItemStack offhandItem = null;
        if (Random.create().nextBoolean()) {
            if (biomeEntry.isIn(ModTags.FIRE_ARROWS)) {
                offhandItem = new ItemStack(ModItems.FIRE_ARROW, 24);
            } else if (biomeEntry.isIn(ModTags.ICE_ARROWS)) {
                offhandItem = new ItemStack(ModItems.ICE_ARROW, 16);
            } else if (biomeEntry.isIn(ModTags.BOMB_ARROWS)) {
                offhandItem = new ItemStack(ModItems.BOMB_ARROW, 8);
            } else if (biomeEntry.isIn(ModTags.SHOCK_ARROWS)) {
                offhandItem = new ItemStack(ModItems.SHOCK_ARROW, 16);
            }

            if (offhandItem != null) {
                entity.equipStack(EquipmentSlot.OFFHAND, offhandItem);
            }
        }
    }
    @Inject(method = "initialize", at = @At("RETURN"))
    private void onInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if ((Object) this instanceof SkeletonEntity skeleton) { equipBiomeArrow(skeleton);}
        if ((Object) this instanceof PillagerEntity pillager) { equipBiomeArrow(pillager);}

        if ((Object) this instanceof StrayEntity stray) {
            stray.equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModItems.ICE_ARROW));
        }
    }
}