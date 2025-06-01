package com.asteristired.Entity.feature;

import com.asteristired.Interface.FrozenTrackerAccessor;
import com.asteristired.StatusEffect.ModStatusEffects;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.LOGGER;
import static com.asteristired.ArthursMod.MOD_ID;

public class FrozenOverlayFeature<T extends LivingEntity, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {

    private static final Identifier FROZEN_TEXTURE = new Identifier(MOD_ID, "textures/entity/frozen_overlay.png");

    public FrozenOverlayFeature(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                       T entity, float limbAngle, float limbDistance, float tickDelta,
                       float animationProgress, float headYaw, float headPitch) {

        if (((FrozenTrackerAccessor) entity).isFreeze()) {
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(FROZEN_TEXTURE));
            this.getContextModel().render(matrices, consumer, light, OverlayTexture.DEFAULT_UV,
                    1f, 1f, 1f, 0.5f);
        }
    }
}