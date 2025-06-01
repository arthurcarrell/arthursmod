package com.asteristired.Enchantment;

import com.asteristired.Sounds.ModSounds;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static com.asteristired.ArthursMod.MOD_ID;

public class AirJumpSFXPacket {

    public static final Identifier packetID = new Identifier(MOD_ID, "air_jump_pkt");

    public static void Register() {
        ServerPlayNetworking.registerGlobalReceiver(packetID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                player.getWorld().playSound(null, player.getBlockPos(), ModSounds.AIR_JUMP_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            });
        });
    }
}
