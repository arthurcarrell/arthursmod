package com.asteristired.FuseItem;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class FuseItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        System.out.println("Rendering FuseItem");
        // get the item renderer
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        // draw the base item, for now will be diamond sword
        itemRenderer.renderItem(itemStack, modelTransformationMode,light,overlay,matrices, vertexConsumers, MinecraftClient.getInstance().world, 0);

        NbtCompound tag = itemStack.getNbt();
        if (tag != null && tag.contains("FuseMaterial")) {
            ItemStack fusedStack = ItemStack.fromNbt(tag.getCompound("FuseMaterial"));
            System.out.println("FuseMaterial: " + fusedStack);

            matrices.push();

            // Position and scale fused item relative to the sword
            matrices.translate(0.25, 0.5, 0.25);
            matrices.scale(0.4f, 0.4f, 0.4f);

            itemRenderer.renderItem(fusedStack, modelTransformationMode,light,overlay,matrices, vertexConsumers, MinecraftClient.getInstance().world, 0);

            matrices.pop();
        }
    }


}