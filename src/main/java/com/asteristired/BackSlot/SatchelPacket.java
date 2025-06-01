package com.asteristired.BackSlot;

import com.asteristired.Item.ModItems;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Optional;

import static com.asteristired.ArthursMod.MOD_ID;

public class SatchelPacket {
    public static final Identifier packetID = new Identifier(MOD_ID, "satchelswap");

    public static void Register() {
        ServerPlayNetworking.registerGlobalReceiver(packetID, (server, player, handler, buf, responseSender) -> {
            int selectedIndex = buf.readInt();

            server.execute(() -> {
                ItemStack selectedItem = player.getMainHandStack();

                ItemStack satchelItem = TrinketsApi.getTrinketComponent(player).flatMap(component ->
                        component.getAllEquipped().stream().findFirst().map(Pair::getRight)
                ).orElse(ItemStack.EMPTY);

                if (satchelItem.getItem() != ModItems.SATCHEL) return;

                assert satchelItem.getNbt() != null;
                // make sure we aren't accessing something that doesn't exist to avoid a crash
                if (!satchelItem.hasNbt() || satchelItem.getItem() != ModItems.SATCHEL || !satchelItem.getNbt().contains("StoredItems")) return;

                // get the item list
                NbtCompound nbt = satchelItem.getNbt();
                NbtList itemList = (NbtList) nbt.get("StoredItems");

                // get the item nbt and convert it to an ItemStack
                assert itemList != null;
                ItemStack grabbedItem = ItemStack.fromNbt((NbtCompound) itemList.get(selectedIndex));
                grabbedItem.setCount(1);

                // if the selected slot has an item in it, store it so the items can be swapped around
                if (selectedItem != ItemStack.EMPTY && selectedItem.getItem() != Items.AIR) {


                    // get the list
                    NbtCompound itemNbt = new NbtCompound();
                    selectedItem.writeNbt(itemNbt);
                    itemList.remove(selectedIndex);
                    itemList.add(selectedIndex, itemNbt);
                    //nbt.put("StoredItems", itemList);

                    // if there is more than one, then decrement the item stack (since one is in the satchel) and throw the rest on the ground
                    if (selectedItem.getCount() > 1) {
                        selectedItem.setCount(selectedItem.getCount()-1);
                        player.dropItem(selectedItem, true);
                    }
                } else {
                    itemList.remove(selectedIndex);
                    //nbt.put("StoredItems", itemList);
                }

                player.getInventory().main.set(player.getInventory().selectedSlot, grabbedItem);
            });
        });
    }
}
