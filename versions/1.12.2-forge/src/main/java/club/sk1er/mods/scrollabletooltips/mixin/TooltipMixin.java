package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.GuiUtilsOverride;
import gg.essential.elementa.font.FontRenderer;
import gg.essential.universal.UGraphics;
import gg.essential.universal.UMatrixStack;
import gg.essential.universal.UMinecraft;
import gg.essential.universal.UScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiUtils.class)
public class TooltipMixin {
    @Unique
    private static final String scrollableTooltips$mixinTarget =
            "drawHoveringText(" +
                    //#if MC>11200
                    "Lnet/minecraft/item/ItemStack;" +
                    //#endif
                    "Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V";
    @Unique
    private static int scrollableTooltips$tooltipY = 0;
    @Unique
    private static int scrollableTooltips$tooltipX = 0;
    @Unique
    private static int scrollableTooltips$tooltipHeight = 0;
    @Unique
    private static final UMatrixStack scrollableTooltips$matrixStack = new UMatrixStack();
    @Unique
    private static Slot scrollableTooltips$currentSlot = null;

    @Inject(method = scrollableTooltips$mixinTarget, at = @At("HEAD"), remap = false)
    private static void scrollableTooltips$detectItemChange(CallbackInfo ci) {
        GuiScreen currentScreen = UMinecraft.getMinecraft().currentScreen;
        if (currentScreen instanceof GuiContainer) {
            Slot hoveredSlot = (((AccessorContainerScreen) currentScreen)).getHoveredSlot();
            if (scrollableTooltips$currentSlot != hoveredSlot) {
                scrollableTooltips$currentSlot = hoveredSlot;
                GuiUtilsOverride.resetScroll();
            }
        }
    }


    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            name = {"tooltipY", "wrappedTooltipWidth"},
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
                    to = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2)
            ),
            remap = false
    )
    private static int scrollableTooltips$captureTooltipY(int tooltipY) {
        scrollableTooltips$tooltipY = tooltipY;
        return 0;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            name = {"tooltipY", "wrappedTooltipWidth"},
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2)
            ),
            remap = false
    )
    private static int scrollableTooltips$ignoreTooltipY(int tooltipY) {
        return 0;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            name = "tooltipHeight",
            remap = false
    )
    private static int scrollableTooltips$captureTooltipHeight(int tooltipHeight) {
        scrollableTooltips$tooltipHeight = tooltipHeight;
        return tooltipHeight;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            name = "tooltipX",
            remap = false
    )
    private static int scrollableTooltips$captureTooltipX(int tooltipX) {
        scrollableTooltips$tooltipX = tooltipX;
        return 0;
    }


    @Inject(
            method = scrollableTooltips$mixinTarget,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/client/config/GuiUtils;drawGradientRect(IIIIIII)V",
                    ordinal = 0
            ),
            remap = false
    )
    private static void scrollableTooltips$pushMatrixAndTranslate(CallbackInfo ci) {
        UGraphics.GL.pushMatrix();
        scrollableTooltips$matrixStack.push();

        // Process tooltipY here since we cancel the original behavior
        if (scrollableTooltips$tooltipY < 4) {
            scrollableTooltips$tooltipY = 4;
        } else if (scrollableTooltips$tooltipY + scrollableTooltips$tooltipHeight + 4 > UScreen.getCurrentScreen().height) {
            scrollableTooltips$tooltipY = UScreen.getCurrentScreen().height - scrollableTooltips$tooltipHeight - 4;
        }

        scrollableTooltips$matrixStack.translate(scrollableTooltips$tooltipX, scrollableTooltips$tooltipY, 0);
        System.out.printf("%d %d%n", scrollableTooltips$tooltipX, scrollableTooltips$tooltipY);
        GuiUtilsOverride.scroll(Mouse.getDWheel());
        GuiUtilsOverride.drawHoveringText(scrollableTooltips$matrixStack, scrollableTooltips$tooltipY, scrollableTooltips$tooltipHeight);
        scrollableTooltips$matrixStack.applyToGlobalState();
    }

    @Inject(
            method = scrollableTooltips$mixinTarget,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;enableRescaleNormal()V",
                    shift = At.Shift.AFTER,
                    ordinal = 0,
                    remap = true
            ),
            remap = false
    )
    private static void scrollableTooltips$popMatrix(CallbackInfo ci) {
        scrollableTooltips$matrixStack.pop();
        UGraphics.GL.popMatrix();
    }
}
