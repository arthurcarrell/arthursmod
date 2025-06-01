package com.asteristired.DamageType;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> MACE_SMASH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(MOD_ID, "mace_smash"));
    public static final RegistryKey<DamageType> SHOCK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(MOD_ID, "shock"));

    public static void Initalise() {}
}
