package com.asteristired.Entity.arrow;

import com.asteristired.Entity.ModModelLayers;
import com.asteristired.Entity.custom.FireArrowEntity;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.util.math.RotationAxis;

import static com.asteristired.ArthursMod.MOD_ID;

public class FireArrowRenderer extends ProjectileEntityRenderer<FireArrowEntity> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/projectile/fire_arrow.png");
    private final ElementalArrowModel model;

    public FireArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new ElementalArrowModel(context.getPart(ModModelLayers.FIRE_ARROW));
    }

    @Override
    public void render(FireArrowEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // rotate arrow to match entity yaw and pitch
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch(tickDelta)));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(getTexture(entity)));

        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(FireArrowEntity entity) {
        return TEXTURE;
    }
}
