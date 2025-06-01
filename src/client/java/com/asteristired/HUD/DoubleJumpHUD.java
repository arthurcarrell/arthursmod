package com.asteristired.HUD;

import com.asteristired.Enchantment.ModEnchantments;
import com.asteristired.Interfaces.IDoubleJumpAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class DoubleJumpHUD {
    private static final Identifier JUMP_ICON_CHG_0 = Identifier.of(MOD_ID, "textures/gui/jump_icon0.png");
    private static final Identifier JUMP_ICON_CHG_1 = Identifier.of(MOD_ID, "textures/gui/jump_icon1.png");
    private static final Identifier JUMP_ICON_CHG_2 = Identifier.of(MOD_ID, "textures/gui/jump_icon2.png");
    private static final Identifier JUMP_ICON_CHG_3 = Identifier.of(MOD_ID, "textures/gui/jump_icon3.png");

    public void Render(DrawContext drawContext, MinecraftClient client) {
        if (client.player != null) {
            int jumpsLeft = ((IDoubleJumpAccess) client.player).arthursmod$getJumpsLeft(); // get amount of jumps here
            boolean showjump = !client.player.isOnGround();

            int screenWidth = drawContext.getScaledWindowWidth();
            int screenHeight = drawContext.getScaledWindowHeight();

            int iconSize = 16;
            int x = screenWidth / 2 - iconSize / 2;
            int y = screenHeight / 2 + 6;

            Identifier jumpIcon = switch (jumpsLeft) {
                case 1 -> JUMP_ICON_CHG_1;
                case 2 -> JUMP_ICON_CHG_2;
                case 3 -> JUMP_ICON_CHG_3;
                default -> JUMP_ICON_CHG_0;
            };

            boolean hasDoubleJump = EnchantmentHelper.getLevel(ModEnchantments.AIR_JUMP, client.player.getInventory().player.getEquippedStack(EquipmentSlot.FEET)) > 0;

            if (showjump && hasDoubleJump) {
                // enable transparency
                drawContext.getMatrices().push();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                // draw
                drawContext.drawTexture(jumpIcon, x, y, 0, 0, iconSize, iconSize, iconSize, iconSize);

                // reset transparency
                RenderSystem.disableBlend();
                drawContext.getMatrices().pop();
            }
        }
    }

}
