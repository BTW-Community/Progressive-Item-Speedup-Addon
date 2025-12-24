package btw.community.arminias.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends EntityPlayer {

    @Unique
    private float prevTimerSpeed = 1.0F;

    @Shadow protected Minecraft mc;

    @Shadow protected abstract float updateGloomFOVMultiplier();

    public EntityPlayerSPMixin(World par1World, String a) {
        super(par1World, a);
    }

    @Redirect(method = "getFOVMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerSP;updateGloomFOVMultiplier()F"))
    private float resetTimerSpeedModifierMixin(EntityPlayerSP entityPlayerSP) {
        float var1 = this.updateGloomFOVMultiplier();
        if (!this.sleeping && this.mc.getTimer().getTimerSpeed() > 1.0F) {
            this.prevTimerSpeed = (float) ((double) this.prevTimerSpeed + (double) (this.mc.getTimer().getTimerSpeed() - this.prevTimerSpeed) * 0.03D);
            var1 *= (float) Math.pow(this.prevTimerSpeed, 0.33);
        } else {
            this.prevTimerSpeed = 1.0F;
        }
        return var1;
    }
}
