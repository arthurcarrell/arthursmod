package com.asteristired.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "damage*", at = @At("HEAD"), cancellable = true)
	private void damage(CallbackInfoReturnable<Integer> info) {
		info.cancel();
	}

	@Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
	private void makeInvincible(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}