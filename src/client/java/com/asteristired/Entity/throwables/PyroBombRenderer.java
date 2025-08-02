package com.asteristired.Entity.throwables;

import com.asteristired.Entity.custom.CryoBombEntity;
import com.asteristired.Entity.custom.PyroBombEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class PyroBombRenderer extends FlyingItemEntityRenderer<PyroBombEntity> {
    public PyroBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
}
