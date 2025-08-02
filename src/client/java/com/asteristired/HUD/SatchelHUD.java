package com.asteristired.HUD;

import com.asteristired.Backslot.Satchel;
import com.asteristired.Item.ModItems;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.apache.http.client.utils.Idn;

import java.util.ArrayList;
import java.util.List;

public class SatchelHUD {

    private void DrawBox(DrawContext drawContext, Identifier SlotTexture, int x, int y, NbtList nbtList, int index, int selectedIndex) {

        // item background
        drawContext.drawTexture(SlotTexture, x+1, y+1, 1, 1, 20, 20);

        // item
        drawContext.drawItem(ItemStack.fromNbt((NbtCompound) nbtList.get(index)), x+3, y+3);

        // selected slot
        if (index == selectedIndex) {
            drawContext.drawTexture(SlotTexture, x-1, y-1, 0, 22, 24, 24);
        }

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

        // prevent the selected index from being grabbed if it is out of bounds.
        // code-wise you cant go out-of-bounds but if the client and the server desync then it can lead to a crash
        if (Satchel.getSelectedIndex() > nbtList.size()) return;

        // selected item
        ItemStack selectedItem = ItemStack.fromNbt((NbtCompound) nbtList.get(Satchel.getSelectedIndex()));
        List<Text> itemText = selectedItem.getTooltip(client.player, TooltipContext.BASIC);

        // drawTooltip only takes a list, but we only want the first element, so we have to make the list just the first element
        // im sure there is a much quicker, easier, cleaner method of doing this.
        List<Text> itemTitle = new ArrayList<Text>();
        itemTitle.add(itemText.get(0));
        int tooltipWidth = client.textRenderer.getWidth(itemTitle.get(0)) + 22;
        int calcX = middleWidth - (tooltipWidth / 2);
        drawContext.drawTooltip(client.textRenderer, itemTitle, calcX, y+40);
    }
}
