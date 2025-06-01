package com.asteristired.Item.ItemClasses;

import com.asteristired.Entity.ModEntities;
import com.asteristired.Entity.custom.OreBulletEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import static com.asteristired.ArthursMod.LOGGER;
import java.util.List;

public class OreBliterator extends Item {

    private final int MAX_AMMO = 6;
    public OreBliterator(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.arthursmod.orebliterator.info").formatted(Formatting.GRAY));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("tooltip.arthursmod.orebliterator.chamber").formatted(Formatting.GRAY));
        for (int i=0; i < MAX_AMMO; i++) {
            tooltip.add(Text.translatable("tooltip.arthursmod.orebliterator.empty").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        LOGGER.info("shot gun");
        OreBulletEntity oreBullet = new OreBulletEntity(world, user);
        oreBullet.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 4.5f, 1.0f);
        oreBullet.setPosition(user.getX(), user.getEyeY(), user.getZ());
        world.spawnEntity(oreBullet);
        return super.use(world, user, hand);
    }
}
