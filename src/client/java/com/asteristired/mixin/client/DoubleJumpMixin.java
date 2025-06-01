package com.asteristired.mixin.client;

import com.asteristired.BackSlot.SwapPacket;
import com.asteristired.Enchantment.AirJumpSFXPacket;
import com.asteristired.Enchantment.ModEnchantments;
import com.asteristired.Interfaces.IDoubleJumpAccess;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class DoubleJumpMixin implements IDoubleJumpAccess {

	@Shadow
	@Final
	public static Logger LOGGER;
	private static int jumpCount = 0;
	private boolean jumpedLastTick = false;
	private final int TICK_OFF_GROUND_REQUIREMENT = 2;
	@Unique
	private int ticksOffGround = 0;

	@Inject(at = @At("HEAD"), method = "tickMovement")
	private void tickMovement(CallbackInfo info) {
		// This code is injected into the start of each tick
		// I got a lot of inspiration and the code from:
		// https://github.com/TurtleArmyMc/DoubleJump/blob/master/src/main/java/com/turtlearmymc/doublejump/mixin/DoubleJumpMixin.java
		// I would have absolutely no idea what I would be doing without this ^^^

		// get the client
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

		// get enchant
		boolean hasDoubleJump = EnchantmentHelper.getLevel(ModEnchantments.AIR_JUMP, player.getEquippedStack(EquipmentSlot.FEET)) > 0;

		// is the player on the ground?
		if (player.isOnGround() || player.isClimbing()) {
			// reset the jump count, which is equal to the level of the enchant.

			if (hasDoubleJump) {
				jumpCount = 3;
			}


		}  else if (!jumpedLastTick && jumpCount > 0 && player.getVelocity().y != 0 && ticksOffGround >= TICK_OFF_GROUND_REQUIREMENT) {
			// in the air, and we CAN double jump
			// check if space key pressed, and we aren't flying in creative mode
			if (player.input.jumping && !player.getAbilities().flying && hasDoubleJump) {
				if (canJump(player)) {
					--jumpCount;
					player.jump();
					player.jump();
					player.fallDistance = 0;

					// send a packet to the server to play a sound effect
					ClientPlayNetworking.send(AirJumpSFXPacket.packetID, PacketByteBufs.empty());
				}
			}
		}
		if (!player.isOnGround() && !player.isClimbing()) {
			if (ticksOffGround < TICK_OFF_GROUND_REQUIREMENT) {
				ticksOffGround++;
			}
		} else {
			ticksOffGround = 0;
		}


		jumpedLastTick = player.input.jumping;
	}


	@Unique
	private boolean wearingUsableElytra(ClientPlayerEntity player) {
		ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
		return chestItemStack.getItem() == Items.ELYTRA;
	}

	@Unique
	private boolean canJump(ClientPlayerEntity player) {
		return !wearingUsableElytra(player) && !player.hasVehicle()
				&& !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION);
	}


	@Override
	public int arthursmod$getJumpsLeft() {
		return jumpCount;
	}
}