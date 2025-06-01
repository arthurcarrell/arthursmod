package com.asteristired.Backslot;

import com.asteristired.BackSlot.SatchelPacket;
import com.asteristired.Item.ModItems;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Pair;

import static com.asteristired.ArthursMod.LOGGER;

public class Satchel {
    static boolean showHUD = false;
    static int selectedIndex = 0;
    static boolean wasShowingHud = false;

    public static boolean shouldShowHUD() {
        return showHUD;
    }

    public static int getSelectedIndex() {
        return selectedIndex;
    }

    public static void KeyPressed(MinecraftClient client) {
    }

    public static void KeyReleased(MinecraftClient client) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(selectedIndex);
        ClientPlayNetworking.send(SatchelPacket.packetID, buf);
    }

    public static void DoKeybindCheck(KeyBinding keybind) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                long window = MinecraftClient.getInstance().getWindow().getHandle();
                showHUD = InputUtil.isKeyPressed(window, keybind.getDefaultKey().getCode());

                if (wasShowingHud && !showHUD) {
                    // just let go
                    LOGGER.info("Key released");
                    KeyReleased(client);
                }
                wasShowingHud = showHUD;
            }
        });
    }

    public static boolean OnScroll(double vertical) { // bool determines if the mouse scroll should be 'eaten'
        MinecraftClient client = MinecraftClient.getInstance();
        ItemStack item = TrinketsApi.getTrinketComponent(client.player).flatMap(component ->
                component.getAllEquipped().stream().findFirst().map(Pair::getRight)
        ).orElse(ItemStack.EMPTY);



        if (showHUD && item.getItem() == ModItems.SATCHEL) {

            // get satchel contents
            assert item.getNbt() != null;
            if (!item.getNbt().contains("StoredItems")) return false;

            NbtList itemList = (NbtList) item.getNbt().get("StoredItems");
            assert itemList != null;

            int direction = (int) -Math.signum(vertical); // 1 for up, -1 for down
            selectedIndex += direction;

            if (selectedIndex > itemList.size()-1) {
                selectedIndex = 0;
            } else if (selectedIndex < 0) {
                selectedIndex = itemList.size()-1;
            }
            LOGGER.info(String.valueOf(selectedIndex));

            return true;
        }

        return false;
    }



}
