package btw.community.arminias.mixin;

import btw.item.items.ProgressiveCraftingItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
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

    @Shadow private ItemStack itemInUse;

    @Shadow public InventoryPlayer inventory;

    @Inject(method = "onItemUseFinish", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;clearItemInUse()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void resetTimerSpeedModifierMixin(CallbackInfo ci, int var1, ItemStack var2) {
        if (var2.getItem() instanceof ProgressiveCraftingItem) {
            this.resetTimerSpeedModifier();
        }
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void resetTimerSpeedModifierMixin(CallbackInfo ci) {
        if (this.itemInUse != null && this.itemInUse.getItem() instanceof ProgressiveCraftingItem) {
            ItemStack var1 = this.inventory.getCurrentItem();
            if (var1 != this.itemInUse &&
                    (var1 == null || !itemInUse.getItem().ignoreDamageWhenComparingDuringUse() ||
                            var1.itemID != itemInUse.itemID || !ItemStack.areItemStackTagsEqual(itemInUse, var1))) {
                this.resetTimerSpeedModifier();
            }
        }
    }
}
