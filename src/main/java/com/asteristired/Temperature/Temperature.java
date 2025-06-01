package com.asteristired.Temperature;

import com.asteristired.Interface.FrozenTrackerAccessor;
import com.asteristired.Tags.ModTags;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;

import static com.asteristired.ArthursMod.LOGGER;
import static com.asteristired.ArthursMod.MOD_ID;

public class Temperature {

    private static boolean NearHeatSource(PlayerEntity player) {
        var world = player.getWorld();
        for (BlockPos pos : BlockPos.iterateOutwards(player.getBlockPos(), 3, 3, 3)) {
            if (world.getBlockState(pos).isIn(ModTags.WARM_BLOCKS)) {
                // Do something
                return true;
            }
        }
        return false;
    }
    public static int GetTemperature(PlayerEntity player) {
        var world = player.getWorld(); // only reason its var is because this is run both client side and server side, and there is ClientWorld and ServerWorld.
        float temperature = player.getWorld().getBiome(player.getBlockPos()).value().getTemperature();

        boolean isSkyVisible = world.isSkyVisible(player.getBlockPos());

        // world.isNight() doesnt work on the client, but the client still has access to the time, so calculate the time manually.
        long time = world.getTimeOfDay() % 24000;
        boolean isNight = time >= 13000 && time <= 23000;

        // stuff that adds heat
        if (!isSkyVisible && !player.isSubmergedInWater()) {
           if (temperature < 0.6) {
               temperature += 0.3f;
           } else if (temperature >= 1.5f ) {
               temperature -= 0.6f;
           }
        }
        if (NearHeatSource(player)) {
            temperature += 0.5f;
        }
        if (player.isOnFire()) {temperature += 9999f; }



        // stuff that removes heat
        if (((FrozenTrackerAccessor) player).isFreeze()) {temperature -= 9999f; }
        if (player.inPowderSnow) { temperature -= 0.5f; }
        if (isSkyVisible) {
            if (isNight) { temperature -= 0.2f; }
            if (isNight && world.getBiome(player.getBlockPos()).matchesKey(BiomeKeys.DESERT)) { temperature -= 1.5f; }
            if (temperature < 0.2f && world.isRaining()) { temperature -= 0.1f;}
        }
        LOGGER.info(String.valueOf(temperature));

        if (temperature >= 9999f) { return 4; }
        if (temperature >= 2.2f) { return 3; }
        if (temperature >= 1.5f) { return 2; }
        if (temperature >= 0.9f) { return 1; }
        if (temperature >= 0.6f) { return 0; }
        if (temperature >= 0.2f) { return -1; }
        if (temperature >= -0.1f) { return -2; }

        if (temperature <= -9999f) { return -4; }

        return -3;
    }

    public static void DoTemperatureCheck(PlayerEntity player, World world) {
        // get the players temp
        int temperature = GetTemperature(player);
        LOGGER.info("done check");
        if (temperature >= 2 && temperature != 4 && world instanceof ServerWorld serverWorld) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            Advancement advancement = serverWorld.getServer().getAdvancementLoader().get(Identifier.of(MOD_ID, "toohot"));
            assert advancement != null;
            if (!serverPlayer.getAdvancementTracker().getProgress(advancement).isDone()) {
                // advancement is being added, run code
                LOGGER.info("got hot for first time");
                TemperaturePacket.SendTooHotPacket(serverPlayer);
            }
            serverPlayer.getAdvancementTracker().grantCriterion(advancement, "correct_temp");
        }
    }
}
