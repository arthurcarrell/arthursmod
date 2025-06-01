package com.asteristired.mixin.client;

import com.asteristired.Backslot.Satchel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        boolean shouldCancel = false;
        if (MinecraftClient.getInstance().currentScreen == null) {
            shouldCancel = Satchel.OnScroll(vertical);
        }

        if (shouldCancel) {
            ci.cancel();
        }
    }
}
