package com.asteristired.Entity;

import com.asteristired.Entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModEntities {

    // this is where mobs are registered
    public static final EntityType<EyefishEntity> EYEFISH = Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID, "eyefish"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EyefishEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static final EntityType<FireArrowEntity> FIRE_ARROW = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "fire_arrow"),
            FabricEntityTypeBuilder.<FireArrowEntity>create(SpawnGroup.MISC, FireArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
    );

    public static final EntityType<IceArrowEntity> ICE_ARROW = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "ice_arrow"),
            FabricEntityTypeBuilder.<IceArrowEntity>create(SpawnGroup.MISC, IceArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
    );

    public static final EntityType<ShockArrowEntity> SHOCK_ARROW = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "shock_arrow"),
            FabricEntityTypeBuilder.<ShockArrowEntity>create(SpawnGroup.MISC, ShockArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
    );

    public static final EntityType<BombArrowEntity> BOMB_ARROW = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "bomb_arrow"),
            FabricEntityTypeBuilder.<BombArrowEntity>create(SpawnGroup.MISC, BombArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
    );

    public static final EntityType<OreBulletEntity> ORE_BULLET =
            Registry.register(Registries.ENTITY_TYPE, new Identifier(MOD_ID, "ore_bullet"),
                    FabricEntityTypeBuilder.<OreBulletEntity>create(SpawnGroup.MISC, OreBulletEntity::new)
                            .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // projectile size
                            .trackRangeChunks(4)
                            .trackedUpdateRate(10)
                            .build()
            );
    public static void Initialise() {
        return;
    }
}
