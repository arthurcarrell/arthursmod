package com.asteristired.HUD;

import com.asteristired.Backslot.Satchel;
import com.asteristired.Item.ModItems;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.apache.http.client.utils.Idn;

public class SatchelHUD {

    private void DrawBox(DrawContext drawContext, Identifier SlotTexture, int x, int y, NbtList nbtList, int index, int selectedIndex) {

        if (index == selectedIndex) {
            drawContext.drawTexture(SlotTexture, x, y, 0, 22, 24, 24);
        }
        drawContext.drawItem(ItemStack.fromNbt((NbtCompound) nbtList.get(index)), x+4, y+3);
    }
    public void Render(DrawContext drawContext, MinecraftClient client) {

        if (!Satchel.shouldShowHUD()) return;

        int middleWidth = drawContext.getScaledWindowWidth()/2;
        int middleHeight = drawContext.getScaledWindowHeight()/2;

        ItemStack item = TrinketsApi.getTrinketComponent(client.player).flatMap(component ->
                component.getAllEquipped().stream().findFirst().map(Pair::getRight)
        ).orElse(ItemStack.EMPTY);

        Identifier SlotTexture = new Identifier("minecraft", "textures/gui/widgets.png");

        int x = (int) (middleWidth)-2;
        int y = (int) (middleHeight)-(24/2);

        assert item.getNbt() != null;
        if (!item.hasNbt() || item.getItem() != ModItems.SATCHEL || !item.getNbt().contains("StoredItems")) return;


        NbtList nbtList = (NbtList) item.getNbt().get("StoredItems");
        assert nbtList != null;

        x -= (24/2) * nbtList.size();
        for (int i=0; i < nbtList.size();i++) {
            DrawBox(drawContext, SlotTexture, x, y, nbtList, i, Satchel.getSelectedIndex());
            x += 24 + 2;
        }


    }
}
