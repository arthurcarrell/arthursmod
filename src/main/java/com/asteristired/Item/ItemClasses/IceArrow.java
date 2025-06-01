package com.asteristired.Item.ItemClasses;

import com.asteristired.Entity.custom.FireArrowEntity;
import com.asteristired.Entity.custom.IceArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IceArrow extends ArrowItem {
    public IceArrow(Settings settings) {
        super(settings);
    }
    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new IceArrowEntity(world, shooter);
    }
}
