package com.asteristired.Packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.asteristired.ArthursMod.LOGGER;
import static com.asteristired.ArthursMod.MOD_ID;

public class MaceImpactPacket {
    public static final Identifier MACE_IMPACT_PACKET = new Identifier(MOD_ID, "mace_impact");

    public static void sendMaceImpactPacket(ServerPlayerEntity player, BlockPos pos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        ServerPlayNetworking.send(player, MACE_IMPACT_PACKET, buf);
    }


    public static void Register() {
        ClientPlayNetworking.registerGlobalReceiver(MACE_IMPACT_PACKET, (client, handler, buf, responseSender) -> {
            BlockPos blockPos = buf.readBlockPos();
            client.execute(() -> {
                World world = client.world;
                if (world != null) {
                    BlockState blockState = world.getBlockState(blockPos);

                    if (blockState.isAir()) return;

                    LOGGER.info("smash attacked grounded entity");

                    BlockStateParticleEffect particle = new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState);


                    for (int i = 0; i < 200; i++) {

                        LOGGER.info("summoning particle");
                        double xOffset = world.random.nextDouble();
                        double yOffset = world.random.nextDouble();
                        double zOffset = world.random.nextDouble();

                        // finally spawn the particle
                        world.addParticle(
                                ParticleTypes.FLAME,
                                blockPos.getX() + xOffset,
                                blockPos.getY() + yOffset,
                                blockPos.getZ() + zOffset,
                                0.1,
                                0.1,
                                0.1
                        );
                    }
                }
            });
        });
    }



}
