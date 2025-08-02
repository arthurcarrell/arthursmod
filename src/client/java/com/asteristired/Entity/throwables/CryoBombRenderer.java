package com.asteristired.Entity.throwables;

import com.asteristired.Entity.custom.BombEntity;
import com.asteristired.Entity.custom.CryoBombEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class CryoBombRenderer extends FlyingItemEntityRenderer<CryoBombEntity> {
    public CryoBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
}
