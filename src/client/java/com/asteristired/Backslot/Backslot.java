package com.asteristired.Backslot;

import com.asteristired.BackSlot.SwapPacket;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import static com.asteristired.ArthursMod.LOGGER;


public class Backslot {

    public static void DoKeyBindCheck(KeyBinding keybind) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keybind.wasPressed()) {
                KeyBindPressed(client);
            }
        });
    }

    public static void KeyBindPressed(MinecraftClient client) {
        assert client.player != null;
        ClientPlayNetworking.send(SwapPacket.packetID, PacketByteBufs.empty());
        Satchel.KeyPressed(client);
    }
}
