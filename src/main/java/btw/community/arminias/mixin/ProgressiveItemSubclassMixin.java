package btw.community.arminias.mixin;

import btw.item.items.*;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {WickerWeavingItem.class, KnittingItem.class, BoneCarvingItem.class, SinewExtractingItem.class, WebUntanglingItem.class})
public class ProgressiveItemSubclassMixin {
    @Inject(method = {"onEaten", "method_3367"}, at = @At("RETURN"), remap = false)
    private void onEatenMixin(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        player.resetTimerSpeedModifier();
    }
}
