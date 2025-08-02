package com.asteristired;

import com.asteristired.Backslot.Backslot;
import com.asteristired.Entity.*;
import com.asteristired.Entity.arrow.*;
import com.asteristired.Entity.custom.FireArrowEntity;
import com.asteristired.Entity.feature.FrozenOverlayFeature;
import com.asteristired.Entity.throwables.BombRenderer;
import com.asteristired.Entity.throwables.CryoBombRenderer;
import com.asteristired.Entity.throwables.PyroBombRenderer;
import com.asteristired.FuseItem.FuseItemRenderer;
import com.asteristired.HUD.*;
import com.asteristired.Item.ModItems;
import com.asteristired.Packets.TemperaturePacketHandler;
import com.asteristired.StatusEffect.ModStatusEffects;
import com.asteristired.keybinds.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import com.asteristired.HUD.DoubleJumpHUD;

import static com.asteristired.ArthursMod.LOGGER;
import static com.asteristired.ArthursMod.MOD_ID;

public class ArthursModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		LOGGER.info("Client initalised");

		// register keybinds
		ModKeybinds.Initalise();
		ModStatusEffects.Initalise();

		// create HUDs
		BackSlotHUD backSlotHUD = new BackSlotHUD();
		DoubleJumpHUD doubleJumpHUD = new DoubleJumpHUD();
		SatchelHUD satchelHUD = new SatchelHUD();
		TemperatureHUD temperatureHUD = new TemperatureHUD();

		// add them to the render callback
		HudRenderCallback.EVENT.register((DrawContext drawContext, float tickDelta) -> {
			MinecraftClient client = MinecraftClient.getInstance();
			backSlotHUD.Render(drawContext, client);
			doubleJumpHUD.Render(drawContext, client);
			satchelHUD.Render(drawContext, client);
			//temperatureHUD.Render(drawContext, client);
		});

		// entities
		EntityRendererRegistry.register(ModEntities.EYEFISH, EyefishRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.EYEFISH, EyefishModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.FIRE_ARROW, FireArrowRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FIRE_ARROW, ElementalArrowModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.ICE_ARROW, IceArrowRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ICE_ARROW, ElementalArrowModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.SHOCK_ARROW, ShockArrowRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SHOCK_ARROW, ElementalArrowModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.BOMB_ARROW, BombArrowRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BOMB_ARROW, ElementalArrowModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.ORE_BULLET, OreBulletRenderer::new);

		EntityRendererRegistry.register(ModEntities.BOMB, BombRenderer::new);
		EntityRendererRegistry.register(ModEntities.CRYOBOMB, CryoBombRenderer::new);
		EntityRendererRegistry.register(ModEntities.PYROBOMB, PyroBombRenderer::new);


		// packet handlers
		TemperaturePacketHandler.Register();

	}
}