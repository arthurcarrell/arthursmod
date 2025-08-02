package com.asteristired.Item;

import com.asteristired.Item.ItemClasses.*;
import com.asteristired.Item.ItemClasses.Copper.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static com.asteristired.ArthursMod.MOD_ID;

public class ModItems {



    public static Item MACE = Register(new Mace(ToolMaterials.DIAMOND, 2.0f, -3.4f, new Item.Settings()
            .rarity(Rarity.EPIC)
    ), "mace");

    public static Item SANGUINE_SICKLE = Register(new SanguineSickle(ToolMaterials.DIAMOND, 0, 1, new Item.Settings()
            .rarity(Rarity.UNCOMMON)
    ), "sanguine_sickle");

    public static Item SATCHEL = Register(new Satchel(new Item.Settings()
            .rarity(Rarity.UNCOMMON)
            .maxCount(1)
    ), "satchel");

    // BOMBS
    public static Item BOMB = Register(new Bomb(new Item.Settings()
    ), "bomb");
    public static Item CRYOBOMB = Register(new CryoBomb(new Item.Settings()
    ), "cryobomb");
    public static Item PYROBOMB = Register(new PyroBomb(new Item.Settings()
    ), "pyrobomb");


    // ARROWS
    public static Item FIRE_ARROW = Register(new FireArrow(new Item.Settings()
    ), "fire_arrow");
    public static Item ICE_ARROW = Register(new IceArrow(new Item.Settings()
    ), "ice_arrow");
    public static Item SHOCK_ARROW = Register(new ShockArrow(new Item.Settings()
    ), "shock_arrow");
    public static Item BOMB_ARROW = Register(new BombArrow(new Item.Settings()
    ), "bomb_arrow");

    public static Item ORE_BLITERATOR = Register(new OreBliterator(new Item.Settings()
            .rarity(Rarity.EPIC)
            .maxCount(1)
    ), "ore_bliterator");

    // COPPER
    public static Item COPPER_NUGGET = Register(new CopperNugget(new Item.Settings()
    ), "copper_nugget");
    public static Item COPPER_AXE = Register(new CopperAxe(CopperToolMaterial.INSTANCE, 7.0F, -3.2f, new Item.Settings()
    ), "copper_axe");
    public static Item COPPER_PICKAXE = Register(new CopperPickaxe(CopperToolMaterial.INSTANCE, 2, -2.8f, new Item.Settings()
    ), "copper_pickaxe");
    public static Item COPPER_SHOVEL = Register(new CopperShovel(CopperToolMaterial.INSTANCE, 2, -3f, new Item.Settings()
    ), "copper_shovel");
    public static Item COPPER_HOE = Register(new CopperHoe(CopperToolMaterial.INSTANCE, 0, -2f, new Item.Settings()
    ), "copper_hoe");
    public static Item COPPER_SWORD = Register(new CopperSword(CopperToolMaterial.INSTANCE, 3, -2.4f, new Item.Settings()
    ), "copper_sword");

    // custom registry system (https://docs.fabricmc.net/1.20.4/develop/items/first-item)
    public static Item Register(Item item, String id) {
        // create the identifier
        Identifier ID = new Identifier(MOD_ID, id);

        // register the item
        Item registeredItem = Registry.register(Registries.ITEM, ID, item);

        // return it
        return registeredItem;
    }

    public static void AddItemsToGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
            itemGroup.addAfter(Items.TRIDENT, ModItems.MACE);
            itemGroup.addAfter(ModItems.MACE, ModItems.SANGUINE_SICKLE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(Items.NETHERITE_HOE, ModItems.SATCHEL);
        });

        // bombs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
            itemGroup.addAfter(Items.TNT, ModItems.BOMB);
            itemGroup.addAfter(ModItems.BOMB, ModItems.CRYOBOMB);
            itemGroup.addAfter(ModItems.CRYOBOMB, ModItems.PYROBOMB);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(Items.MILK_BUCKET, ModItems.BOMB);
            itemGroup.addAfter(ModItems.BOMB, ModItems.CRYOBOMB);
            itemGroup.addAfter(ModItems.CRYOBOMB, ModItems.PYROBOMB);
        });

        // Copper
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> {
            itemGroup.addAfter(Items.IRON_NUGGET, ModItems.COPPER_NUGGET);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(Items.STONE_HOE, ModItems.COPPER_SHOVEL);
            itemGroup.addAfter(ModItems.COPPER_SHOVEL, ModItems.COPPER_PICKAXE);
            itemGroup.addAfter(ModItems.COPPER_PICKAXE, ModItems.COPPER_AXE);
            itemGroup.addAfter(ModItems.COPPER_AXE, ModItems.COPPER_HOE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
            itemGroup.addAfter(Items.STONE_SWORD, ModItems.COPPER_SWORD);
            itemGroup.addAfter(Items.STONE_AXE, ModItems.COPPER_AXE);
        });

    }


    public static void Initalise() {
        return;
    }
}
