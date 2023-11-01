package btw.community.arminias.mixin;

import btw.item.items.*;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProgressiveCraftingItem.class)
public abstract class ProgressiveCraftingItemMixin extends Item {
    public ProgressiveCraftingItemMixin(int par1) {
        super(par1);
    }

    @Inject(method = "updateUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;getItemDamage()I"))
    private void updateUsingItemMixin(ItemStack stack, World world, EntityPlayer player, CallbackInfo ci) {
        player.setTimerSpeedModifier(player.timerSpeedModifier + 0.013F / player.timerSpeedModifier);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
        super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
        par3EntityPlayer.resetTimerSpeedModifier();
    }
}
