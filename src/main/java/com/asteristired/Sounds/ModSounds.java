package com.asteristired.Sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModSounds {

    public static final Identifier AIR_JUMP = Identifier.of(MOD_ID, "air_jump");
    public static SoundEvent AIR_JUMP_EVENT = SoundEvent.of(AIR_JUMP);

    public static final Identifier MACE_SMASH = Identifier.of(MOD_ID, "mace_smash");
    public static SoundEvent MACE_SMASH_EVENT = SoundEvent.of(MACE_SMASH);

    public static final Identifier MACE_HEAVY_SMASH = Identifier.of(MOD_ID, "mace_heavy_smash");
    public static SoundEvent MACE_HEAVY_SMASH_EVENT = SoundEvent.of(MACE_HEAVY_SMASH);
}
