package com.asteristired.Item.ItemClasses;

import com.asteristired.Entity.custom.FireArrowEntity;
import com.asteristired.Item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FireArrow extends ArrowItem {
    public FireArrow(Settings settings) {
        super(settings);
    }
    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new FireArrowEntity(world, shooter);
    }
}
