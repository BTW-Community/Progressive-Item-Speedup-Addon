package btw.community.arminias.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {
    public float prevTimerSpeed = 1.0F;
    @Shadow @Final private Minecraft mc;

    @Inject(method = "renderVignette", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", shift = At.Shift.AFTER, ordinal = 0), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void renderVignetteMixin(float par1, int par2, int par3, CallbackInfo ci, Tessellator var4) {
        if (!this.mc.thePlayer.isPlayerSleeping() && this.mc.getTimer().getTimerSpeed() > 1.0F) {
            this.prevTimerSpeed = (float) ((double) this.prevTimerSpeed + (double) (this.mc.getTimer().getTimerSpeed() - this.prevTimerSpeed) * 0.005D);
            par1 = (this.prevTimerSpeed - 1.0F) * 0.66F;
            GL11.glColor4f(par1, par1, par1, 1.0F);
            this.mc.renderEngine.bindTexture("%blur%/misc/strongVignette.png");
            var4.startDrawingQuads();
            var4.addVertexWithUV(0.0D, (double) par3, -90.0D, 0.0D, 1.0D);
            var4.addVertexWithUV((double) par2, (double) par3, -90.0D, 1.0D, 1.0D);
            var4.addVertexWithUV((double) par2, 0.0D, -90.0D, 1.0D, 0.0D);
            var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
            var4.draw();
        } else {
            this.prevTimerSpeed = 1.0F;
        }
    }
}
