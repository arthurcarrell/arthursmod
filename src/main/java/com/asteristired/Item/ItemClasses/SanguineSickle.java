package com.asteristired.Item.ItemClasses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Set;

public class SanguineSickle extends SwordItem {

    /*
    *
    * Sanguine Sickle
    * Low damage, high attack speed weapon
    * gains blood by attacking enemies
    * when blood bar is full, spend blood to inflict hemorrage and:
    * if the enemy is in the air, root them into the ground
    * if the enemy is on the ground, knock them into the air
    * you can also spend 15 blood to dash forward slightly
    *  - this weapon is designed for very aggressive fighting
    * */

    private static final int MAX_BLOOD = 100;
    private static final int BLOOD_PER_HIT = 25;

    private static final int DASH_COST = 25;
    private static final float DASH_SPEED = 1f;

    public SanguineSickle(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        // get item in hand
        ItemStack stack = user.getStackInHand(hand);

        if (GetBlood(stack) >= DASH_COST && !world.isClient) {

            // direction of dash
            Vec3d direction = user.getRotationVec(1.0f);
            user.addVelocity(direction.multiply(DASH_SPEED));
            user.velocityModified = true;

            // spend blood
            SetBlood(stack, GetBlood(stack) - DASH_COST);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        int blood = GetBlood(stack);

        if (blood >= MAX_BLOOD) {
            // spend the blood to knock up the entity hit
            if (target.isOnGround()) {
                target.addVelocity(new Vec3d(0, 0.5, 0));
            } else {
                target.addVelocity(new Vec3d(0, -2, 0));
            }
            SetBlood(stack, 0);

            // heal hp
            attacker.heal(4);
        } else {
            // increment blood by BLOOD_PER_HIT, making sure the number doesn't go above MAX_BLOOD.
            // It would be best to use Math.Clamp() but that was only added in Java 21.
            blood += BLOOD_PER_HIT;

            // if it's a critical hit, add double the amount of blood
            if (!attacker.isOnGround() && !attacker.hasStatusEffect(StatusEffects.SLOW_FALLING)) {blood += BLOOD_PER_HIT;}

            if (blood > MAX_BLOOD) {blood = MAX_BLOOD;}
            SetBlood(stack, blood);
        }


        return super.postHit(stack, target, attacker);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (GetBlood(stack) >= MAX_BLOOD) {
            return 0xFF2C2C; // stronger red
        } else {
            return 0xFF6060; // light red
        }
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true; // the blood bar should always be visible
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        // for some reason: 1.0 is empty and 0.0 is full.
        return Math.round(13.0f * GetBlood(stack) / MAX_BLOOD); // the durability bar is 13 pixels wide
    }

    public static void SetBlood(ItemStack itemStack, int blood) {
        NbtCompound nbt = itemStack.getOrCreateNbt();
        nbt.putInt("Blood", blood);
    }

    public static int GetBlood(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getOrCreateNbt();
        return nbt.getInt("Blood");

    }
}
