package com.asteristired.StatusEffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;
public class ModStatusEffects {

    public static final StatusEffect FROZEN_EFFECT = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "frozen"),
            new FrozenEffect(StatusEffectCategory.HARMFUL, 0x95eaff));
    public static final StatusEffect SHOCK_EFFECT = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "shock"),
            new ShockEffect(StatusEffectCategory.HARMFUL, 0xebcd5d));

    public static final StatusEffect CC_IMMUNE = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "cc_immune"),
            new CCImmuneEffect());

    public static void Initalise() {}
}

