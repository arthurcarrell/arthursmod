package com.asteristired.Statistics;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModStatistics {
    public static final Identifier SMASH_ATTACKS_PERFORMED = Identifier.of(MOD_ID, "smash_attacks_performed");

    public static void Register() {
        Registry.register(Registries.CUSTOM_STAT, SMASH_ATTACKS_PERFORMED, SMASH_ATTACKS_PERFORMED);
        Stats.CUSTOM.getOrCreateStat(SMASH_ATTACKS_PERFORMED);
    }

    public static void Initalise() {
        Register();
    }
}
