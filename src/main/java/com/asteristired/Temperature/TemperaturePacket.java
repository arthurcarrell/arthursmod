package com.asteristired.Temperature;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class TemperaturePacket {
    public static final Identifier TOOHOT_PACKET = Identifier.of(MOD_ID, "temp_warn");

    public static void SendTooHotPacket(ServerPlayerEntity serverPlayer) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString("toohot");
        assert TOOHOT_PACKET != null;
        ServerPlayNetworking.send(serverPlayer, TOOHOT_PACKET, buf);
    }
}
