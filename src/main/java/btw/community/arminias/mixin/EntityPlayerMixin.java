package btw.community.arminias.mixin;

import btw.item.items.ProgressiveCraftingItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Shadow public abstract void resetTimerSpeedModifier();

    @Inject(method = "onItemUseFinish", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;clearItemInUse()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void resetTimerSpeedModifierMixin(CallbackInfo ci, int var1, ItemStack var2) {
        if (var2.getItem() instanceof ProgressiveCraftingItem) {
            this.resetTimerSpeedModifier();
        }
    }
}
