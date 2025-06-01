package com.asteristired.StatusEffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

// this effect should ALWAYS be hidden from the player. All it does it prevent you from being cc'ed to death
// being hit by something that freezes/shocks will give you this buff secretly, preventing you from being frozen/shocked for a bit (so you can fight back)

// because of what the effect does, it's all calculated in other scripts, this doesn't actually do anything itself.
public class CCImmuneEffect extends StatusEffect {
    public CCImmuneEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xffffff);
    }

}
