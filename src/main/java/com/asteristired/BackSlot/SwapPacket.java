package com.asteristired.BackSlot;

import com.asteristired.Item.ModItems;
import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.apache.commons.logging.Log;

import java.util.Optional;

import static com.asteristired.ArthursMod.LOGGER;
import static com.asteristired.ArthursMod.MOD_ID;
public class SwapPacket {

    public static final Identifier packetID = new Identifier(MOD_ID, "swapslot");

    public static void Register() {
        ServerPlayNetworking.registerGlobalReceiver(packetID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ItemStack selectedItem = player.getMainHandStack();

                TrinketsApi.getTrinketComponent(player).ifPresent(trinketComponent -> {
                    var equipped = Optional.ofNullable(trinketComponent.getInventory().get("chest")).map(group -> group.get("back"));

                    if (equipped.isEmpty()) return;

                    TrinketInventory inventory = equipped.get();

                    ItemStack item = inventory.getStack(0);

                    if (item.getItem() == ModItems.SATCHEL) return;

                    inventory.setStack(0, selectedItem);
                    player.getInventory().main.set(player.getInventory().selectedSlot, item);
                });
            });
        });
    }
}
