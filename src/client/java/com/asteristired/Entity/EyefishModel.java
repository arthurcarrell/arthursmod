package com.asteristired.Entity;

import com.asteristired.Entity.animation.EyefishAnim;
import com.asteristired.Entity.custom.EyefishEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class EyefishModel<T extends EyefishEntity> extends SinglePartEntityModel<T> {
	private final ModelPart eyefish;

	public EyefishModel(ModelPart root) {
		this.eyefish = root.getChild("eyefish");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData eyefish = modelPartData.addChild("eyefish", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -6.0F, -4.0F, 1.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, 0.0F));

		ModelPartData fins = eyefish.addChild("fins", ModelPartBuilder.create().uv(10, 12).cuboid(-3.0F, 0.0F, -2.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F))
				.uv(0, 12).cuboid(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData tail_group = eyefish.addChild("tail_group", ModelPartBuilder.create().uv(0, 15).cuboid(-0.5F, -2.0F, 0.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 3.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(EyefishEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform); // reset animation, very important!
		this.updateAnimation(entity.swimAnimationState, EyefishAnim.swim, ageInTicks, 1f);
		this.updateAnimation(entity.flopAnimationState, EyefishAnim.flop, ageInTicks, 1f);

	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		eyefish.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return eyefish;
	}
}