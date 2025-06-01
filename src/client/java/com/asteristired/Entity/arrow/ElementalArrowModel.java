package com.asteristired.Entity.arrow;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;

public class ElementalArrowModel<T extends ArrowEntity> extends EntityModel<T> {
    private final ModelPart shaft;
    private final ModelPart fletching;

    public ElementalArrowModel(ModelPart root) {
        this.shaft = root.getChild("shaft");
        this.fletching = root.getChild("fletching");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild("shaft",
                ModelPartBuilder.create()
                        .cuboid(-1.0F, -1.0F, -7.0F, 2, 2, 14),
                ModelTransform.NONE);

        root.addChild("fletching",
                ModelPartBuilder.create()
                        .cuboid(0.0F, -2.0F, 6.0F, 0, 0, 0),
                ModelTransform.NONE);

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // no animation for arrows
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        shaft.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        fletching.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}