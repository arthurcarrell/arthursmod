package com.asteristired.Item;

import com.asteristired.Item.ItemClasses.*;
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
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(ModItems.MACE, ModItems.SANGUINE_SICKLE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(Items.NETHERITE_HOE, ModItems.SATCHEL);
        });
    }


    public static void Initalise() {
        return;
    }
}
