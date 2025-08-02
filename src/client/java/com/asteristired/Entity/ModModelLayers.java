package com.asteristired.Entity;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer EYEFISH =
            new EntityModelLayer(new Identifier(MOD_ID, "eyefish"), "main");

    public static final EntityModelLayer FIRE_ARROW = new EntityModelLayer(
            new Identifier(MOD_ID, "fire_arrow"), "main");
    public static final EntityModelLayer ICE_ARROW = new EntityModelLayer(
            new Identifier(MOD_ID, "ice_arrow"), "main");
    public static final EntityModelLayer SHOCK_ARROW = new EntityModelLayer(
            new Identifier(MOD_ID, "shock_arrow"), "main");
    public static final EntityModelLayer BOMB_ARROW = new EntityModelLayer(
            new Identifier(MOD_ID, "bomb_arrow"), "main");
    public static final EntityModelLayer BOMB = new EntityModelLayer(
            new Identifier(MOD_ID, "bomb"), "main");
    public static final EntityModelLayer CRYOBOMB = new EntityModelLayer(
            new Identifier(MOD_ID, "cryobomb"), "main");
    public static final EntityModelLayer PYROBOMB = new EntityModelLayer(
            new Identifier(MOD_ID, "pyrobomb"), "main");
}
