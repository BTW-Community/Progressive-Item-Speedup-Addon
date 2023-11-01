package btw.community.arminias.mixin;

import btw.item.items.ProgressiveCraftingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends EntityPlayer {

    @Shadow protected Minecraft mc;

    @Shadow protected abstract float updateGloomFOVMultiplier();

    public EntityPlayerSPMixin(World par1World) {
        super(par1World);
    }

    @Redirect(method = "getFOVMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerSP;updateGloomFOVMultiplier()F"))
    private float resetTimerSpeedModifierMixin(EntityPlayerSP entityPlayerSP) {
        float var1 = this.updateGloomFOVMultiplier();
        if (!this.sleeping && this.mc.getTimer().getTimerSpeed() > 1.0F) {
            var1 *= (float) Math.pow(this.mc.getTimer().getTimerSpeed(), 0.33);
        }
        return var1;
    }
}
