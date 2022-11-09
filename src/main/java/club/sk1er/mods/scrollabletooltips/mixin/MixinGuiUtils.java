package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.GuiUtilsOverride;
import gg.essential.universal.UMatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiUtils.class)
public class MixinGuiUtils {
    @Unique
    private static int scrollableTooltips$tooltipY = 0;
    @Unique
    private static int scrollableTooltips$tooltipX = 0;
    @Unique
    private static int scrollableTooltips$tooltipHeight = 0;
    @Unique
    private static final UMatrixStack scrollableTooltips$matrixStack = new UMatrixStack();

    @ModifyVariable(
            method = "drawHoveringText",
            at = @At("STORE"),
            name = {"tooltipY", "wrappedTooltipWidth"},
            remap = false
    )
    private static int scrollableTooltips$captureTooltipY(int tooltipY) {
        scrollableTooltips$tooltipY = tooltipY;
        return 0;
    }

    @ModifyVariable(
            method = "drawHoveringText",
            at = @At("STORE"),
            name = "tooltipHeight",
            remap = false
    )
    private static int scrollableTooltips$captureTooltipHeight(int tooltipHeight) {
        scrollableTooltips$tooltipHeight = tooltipHeight;
        return tooltipHeight;
    }

    @ModifyVariable(
            method = "drawHoveringText",
            at = @At("STORE"),
            name = "tooltipX",
            slice = @Slice(
                    from = @At(value = "NEW", target = "Ljava/util/ArrayList;<init>()V")
            ),
            remap = false
    )
    private static int scrollableTooltips$captureTooltipX(int tooltipX) {
        scrollableTooltips$tooltipX = tooltipX;
        return 0;
    }


    @Inject(
            method = "drawHoveringText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/config/GuiUtils;drawGradientRect(IIIIIII)V",
                    ordinal = 0
            ),
            remap = false
    )
    private static void scrollableTooltips$pushMatrixAndTranslate(List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font, CallbackInfo ci) {
        scrollableTooltips$matrixStack.push();
        scrollableTooltips$matrixStack.translate(scrollableTooltips$tooltipX, scrollableTooltips$tooltipY, 0.0);
        GuiUtilsOverride.drawHoveringText(scrollableTooltips$matrixStack, textLines, screenHeight, scrollableTooltips$tooltipY, scrollableTooltips$tooltipHeight);
    }

    @Inject(
            method = "drawHoveringText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;enableRescaleNormal()V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ),
            remap = false
    )
    private static void scrollableTooltips$popMatrix(List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font, CallbackInfo ci) {
        scrollableTooltips$matrixStack.pop();
    }
}
