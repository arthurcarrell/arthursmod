package com.asteristired.Entity.throwables;

import com.asteristired.Entity.custom.BombEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class BombRenderer extends FlyingItemEntityRenderer<BombEntity> {
    public BombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
}
