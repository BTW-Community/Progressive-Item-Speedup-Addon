package btw.community.arminias.mixin;

import api.item.items.ProgressiveCraftingItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProgressiveCraftingItem.class)
public abstract class ProgressiveCraftingItemMixin extends Item {
    public ProgressiveCraftingItemMixin(int par1) {
        super(par1);
    }

    @Inject(method = "updateUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;getItemDamage()I"))
    private void updateUsingItemMixin(ItemStack stack, World world, EntityPlayer player, CallbackInfo ci) {
        if (!world.isRemote) {
            player.setTimerSpeedModifier(player.getTimerSpeedModifier() + 0.013F / player.getTimerSpeedModifier());
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "updateUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;getItemDamage()I"))
    private void updateUsingItemMixinClient(ItemStack stack, World world, EntityPlayer player, CallbackInfo ci) {
        if (!world.isRemote && player.getItemInUseCount() % 20 == 0 && MinecraftServer.getServer() instanceof IntegratedServer) {
            MinecraftServer.getServer().sendTimerSpeedImmediately();
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
        super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
        par3EntityPlayer.resetTimerSpeedModifier();
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.resetTimerSpeedModifier();
        return super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
    }
}
