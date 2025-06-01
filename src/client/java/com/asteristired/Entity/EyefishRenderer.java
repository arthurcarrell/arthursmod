package com.asteristired.Entity;

import com.asteristired.Entity.custom.EyefishEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.asteristired.ArthursMod.MOD_ID;

public class EyefishRenderer extends MobEntityRenderer<EyefishEntity, EyefishModel<EyefishEntity>> {

    private static final Identifier TEXTURE = new Identifier(MOD_ID,  "textures/entity/eyefish.png");
    public EyefishRenderer(EntityRendererFactory.Context context) {
        super(context, new EyefishModel<>(context.getPart(ModModelLayers.EYEFISH)), 0.6f); // f = size of the shadow of the entity
    }

    @Override
    public Identifier getTexture(EyefishEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(EyefishEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {



        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
