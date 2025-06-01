package com.asteristired.Enchantment;

import com.asteristired.Item.ItemClasses.Mace;
import com.asteristired.Item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class WindBurstEnchantment extends Enchantment {

    /*
    * None of the enchant effects are actually here because it is handled in the Mace.
    */

    public WindBurstEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    @Override // do not allow unless the item is a mace.
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof Mace;
    }

    @Override
    public int getMinPower(int level) {
        return 20;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
