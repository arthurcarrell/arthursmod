package com.asteristired.HUD;

import com.asteristired.Enchantment.ModEnchantments;
import com.asteristired.Interfaces.IDoubleJumpAccess;
import com.asteristired.Item.ModItems;
import com.asteristired.Temperature.Temperature;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import static com.asteristired.ArthursMod.MOD_ID;

public class TemperatureHUD {

    private boolean SHOW_TOO_HOT = false;

    public void ShowTooHot() {
        SHOW_TOO_HOT = true;
    }

    public void Render(DrawContext drawContext, MinecraftClient client) {
        int middleWidth = drawContext.getScaledWindowWidth()/2;
        int middleHeight = drawContext.getScaledWindowHeight()/2;

        assert client.player != null;
        int temperature = Temperature.GetTemperature(client.player);
        Identifier tempBackground = Identifier.of(MOD_ID, "textures/gui/temperature/heatres_0_coldres_0.png");
        String tempSelector = "textures/gui/temperature/selector/temp" + temperature + ".png";
        if (temperature == 4) {
            tempBackground = Identifier.of(MOD_ID, "textures/gui/temperature/on_fire.png");
        } else if (temperature == -4 ) {
            tempBackground = Identifier.of(MOD_ID, "textures/gui/temperature/frozen.png");
        }

        Identifier selector = Identifier.of(MOD_ID, tempSelector);


        int x = (int) (middleWidth)-8;
        int y = (int) (middleHeight*2)-55;

        //drawContext.drawTexture(texture, x-3, middleHeight, 0, 0, 32, 32);
        drawContext.drawTexture(tempBackground, x, y, 0, 0, 16, 16, 16, 16);
        drawContext.drawTexture(selector, x, y, 0, 0, 16, 16, 16, 16);


        Text text = Text.literal("You're entering an area that is too hot!");
        Text text2 = Text.literal("Try finding some shade or coming back at night.");
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        drawContext.fill(middleWidth*2-textRenderer.getWidth(text2)-4, (int) (drawContext.getScaledWindowHeight()*0.1)-4, middleWidth*2+2, textRenderer.fontHeight*2+4+(int) (drawContext.getScaledWindowHeight()*0.1), 0xAA434343);
        drawContext.drawText(textRenderer,text,middleWidth*2-textRenderer.getWidth(text2)-2, (int) (drawContext.getScaledWindowHeight()*0.1), 0xffffff, true);
        drawContext.drawText(textRenderer,text2,middleWidth*2-textRenderer.getWidth(text2)-2, (int) (drawContext.getScaledWindowHeight()*0.1)+textRenderer.fontHeight+2, 0xffffff, true);
    }

}
