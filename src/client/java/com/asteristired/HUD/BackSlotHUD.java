package com.asteristired.HUD;

import com.asteristired.Item.ModItems;
import dev.emi.trinkets.TrinketsClient;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import static com.asteristired.ArthursMod.MOD_ID;

public class BackSlotHUD {

    public void Render(DrawContext drawContext, MinecraftClient client) {
        int middleWidth = drawContext.getScaledWindowWidth()/2;
        int middleHeight = drawContext.getScaledWindowHeight()/2;

        ItemStack item = TrinketsApi.getTrinketComponent(client.player).flatMap(component ->
                component.getAllEquipped().stream().findFirst().map(Pair::getRight)
        ).orElse(ItemStack.EMPTY);

        Identifier SlotTexture = new Identifier("minecraft", "textures/gui/widgets.png");

        int x = (int) (middleWidth)+100;
        int y = (int) (middleHeight*2)-19;

        if (item != ItemStack.EMPTY) {
            if (item.getItem() != ModItems.SATCHEL) {
                drawContext.drawTexture(SlotTexture, x-3, y-4, 24, 22, 24, 24);
            }
            drawContext.drawItem(item, x, y);
        }

    }
}
