package com.asteristired.Tags;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModTags {
    public static final TagKey<Biome> FIRE_ARROWS = TagKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "fire_arrows"));
    public static final TagKey<Biome> ICE_ARROWS = TagKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "ice_arrows"));
    public static final TagKey<Biome> SHOCK_ARROWS = TagKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "shock_arrows"));
    public static final TagKey<Biome> BOMB_ARROWS = TagKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "bomb_arrows"));
    public static final TagKey<Block> WARM_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "warm_blocks"));

    public static void Initalise() {}
}
