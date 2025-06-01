package com.asteristired.Enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModEnchantments {
    public static Enchantment AIR_JUMP = new AirJumpEnchantment();
    public static Enchantment WIND_BURST = new WindBurstEnchantment();
    public static Enchantment KNOCKUP = new KnockUpEnchantment();

    private static void RegisterEnchants() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "air_jump"), AIR_JUMP);
        Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "wind_burst"), WIND_BURST);
        Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "knockup"), KNOCKUP);
    }
    public static void Initialise() {
        RegisterEnchants();
    }
}
