package com.asteristired.Entity;


import com.asteristired.Entity.custom.OreBulletEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// "renderer" - doesnt do anything lmao
public class OreBulletRenderer extends ProjectileEntityRenderer<OreBulletEntity> {
    public OreBulletRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(OreBulletEntity entity) {
        return null;
    }

    @Override
    public void render(OreBulletEntity persistentProjectileEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        // nothing
    }

    @Override
    public boolean shouldRender(OreBulletEntity entity, Frustum frustum, double x, double y, double z) {
        return false;
    }
}
