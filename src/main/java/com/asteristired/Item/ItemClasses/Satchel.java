package com.asteristired.Item.ItemClasses;

import com.asteristired.Item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.asteristired.ArthursMod.LOGGER;
import java.util.List;

public class Satchel extends Item {

    public int maxItemCapacity() { return 5; }

    public Satchel(Settings settings) {
        super(settings);
    }

    private void createItemNBTData(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtList nbtList = new NbtList();
        if (!nbt.contains("StoredItems")) {
            LOGGER.info("creating StoredItems");
            nbt.put("StoredItems", nbtList);
        }
    }

    public void placeItem(ItemStack stack, ItemStack itemToAdd) {
        // reduce the stack size to one
        stack.setCount(1);

        NbtCompound nbt = stack.getNbt();
        LOGGER.info("added item");

        assert nbt != null;
        NbtList nbtList = (NbtList) nbt.get("StoredItems");

        assert nbtList != null;

        NbtCompound itemNbt = new NbtCompound();
        itemToAdd.writeNbt(itemNbt);
        nbtList.add(itemNbt);

        nbt.put("StoredItems", nbtList);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        createItemNBTData(stack);

        // brief description of the item
        tooltip.add(Text.translatable("tooltip.arthursmod.satchel.info", maxItemCapacity()).formatted(Formatting.GRAY));

        tooltip.add(Text.literal("")); // empty line

        // what does the item have?
        NbtCompound nbt = stack.getNbt();

        assert nbt != null;
        NbtList nbtList = (NbtList) nbt.get("StoredItems");

        for (int i=0; i < maxItemCapacity();i++) {
            Text itemText = Text.translatable("tooltip.arthursmod.satchel.emptyslot");

            assert nbtList != null;
            if (nbtList.size() >= i) {
                NbtCompound currentNbt = nbtList.getCompound(i);

                if (currentNbt.contains("id")) {
                    ItemStack item = ItemStack.fromNbt(currentNbt);
                    itemText = item.getName();
                }
            }

            tooltip.add(itemText);
        }

        //tooltip.add(Text.literal("")); // empty line

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        createItemNBTData(stack);

        assert stack.getNbt() != null;
        NbtList nbtList = (NbtList) stack.getNbt().get("StoredItems");

        assert nbtList != null;
        int size = nbtList.size();

        if (otherStack != null && otherStack != ModItems.SATCHEL.getDefaultStack() && !otherStack.toString().equals("0 air")) {

            // get nbt data
            if (clickType.equals(ClickType.LEFT)) {
                if (size < maxItemCapacity()) {
                    LOGGER.info(otherStack.toString());
                    placeItem(stack, otherStack);
                    otherStack.decrement(1);
                    return true;
                }
            }
        }

        if (otherStack == null || otherStack.toString().equals("0 air")) {
            if (size > 0 && clickType.equals(ClickType.RIGHT)) {

                // set the item in the cursor to be the item in the satchel
                ItemStack item = ItemStack.fromNbt((NbtCompound) nbtList.get(size-1));
                cursorStackReference.set(item);
                // remove the item from the satchel
                nbtList.remove(size-1);
                return true;
            }
        }
        return false;
    }
}
