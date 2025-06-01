package com.asteristired.mixin;

import com.asteristired.Interface.FrozenTrackerAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class FrozenTrackerMixin extends Entity implements FrozenTrackerAccessor {

    private static final TrackedData<Boolean> IS_FROZEN =
            DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public FrozenTrackerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void onInitDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(IS_FROZEN, false);
    }

    @Unique
    public void setFreeze(boolean frozen) {
        this.dataTracker.set(IS_FROZEN, frozen);
    }

    public boolean isFreeze() {
        return this.dataTracker.get(IS_FROZEN);
    }
}
