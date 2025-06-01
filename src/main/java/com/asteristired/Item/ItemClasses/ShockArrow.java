package com.asteristired.Item.ItemClasses;

import com.asteristired.Entity.custom.ShockArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShockArrow extends ArrowItem {
    public ShockArrow(Settings settings) {
        super(settings);
    }
    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new ShockArrowEntity(world, shooter);
    }
}
