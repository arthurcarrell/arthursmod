package com.asteristired.Packets;

import com.asteristired.Temperature.TemperaturePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import static com.asteristired.ArthursMod.LOGGER;
public class TemperaturePacketHandler {
    public static void Register() {
        assert TemperaturePacket.TOOHOT_PACKET != null;
        ClientPlayNetworking.registerGlobalReceiver(TemperaturePacket.TOOHOT_PACKET, (client, handler, buf, responseSender) -> {
            String warnType = buf.readString();

            client.execute(() -> {
                LOGGER.info("Running warning for: {}", warnType);
            });
        });
    }
}
