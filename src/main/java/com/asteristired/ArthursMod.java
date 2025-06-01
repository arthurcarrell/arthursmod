package com.asteristired;

import com.asteristired.BackSlot.SatchelPacket;
import com.asteristired.BackSlot.SwapPacket;
import com.asteristired.Block.ModBlocks;
import com.asteristired.DamageType.ModDamageTypes;
import com.asteristired.Enchantment.AirJumpSFXPacket;
import com.asteristired.Enchantment.ModEnchantments;
import com.asteristired.Entity.ModEntities;
import com.asteristired.Entity.custom.EyefishEntity;
import com.asteristired.Item.ModItems;
import com.asteristired.StatusEffect.ModStatusEffects;
import com.asteristired.Tags.ModTags;
import com.asteristired.Temperature.Temperature;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.security.auth.callback.Callback;

public class ArthursMod implements ModInitializer {
	public static final String MOD_ID = "arthursmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		// register packet stuff
		SwapPacket.Register();
		SatchelPacket.Register();
		AirJumpSFXPacket.Register();

		// Initalise modules
		ModStatusEffects.Initalise();
		ModEnchantments.Initialise();
		ModItems.Initalise();
		ModItems.AddItemsToGroups();
		ModDamageTypes.Initalise();
		ModEntities.Initialise();
		ModBlocks.Initialise();
		ModTags.Initalise();


		// attribute stuff
		FabricDefaultAttributeRegistry.register(ModEntities.EYEFISH, EyefishEntity.CreateEyefishAttributes());

		// callbacks
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				Temperature.DoTemperatureCheck(player, player.getServerWorld());
			}
		});
	}
}