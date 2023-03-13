package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.GuiUtilsOverride;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.essential.universal.UMatrixStack;
import gg.essential.universal.UMinecraft;
import gg.essential.universal.UScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

//FIXME: Use sugars when possible
@Mixin(Screen.class)
public class TooltipMixin {
    // Mixin Constants
    @Unique
    private static final String scrollableTooltips$mixinTarget =
            //#if FORGE
            //#if MC<11700
            "renderToolTip";
            //#else
            //$$ "renderTooltipInternal";
            //#endif
            //#endif
            //#if FABRIC
            //$$ "renderOrderedTooltip";
            //#endif
    @Unique
    private static final int scrollableTooltips$tooltipYOrdinal =
            4;
    @Unique
    private static final int scrollableTooltips$tooltipHeightOrdinal =
            //#if FABRIC
            //$$ 6;
            //#else
            5;
            //#endif
    @Unique
    private static final int scrollableTooltips$tooltipXOrdinal =
            //#if FABRIC
            //$$ 3;
            //#else
            3;
            //#endif
    @Unique
    private static final int scrollableTooltips$tooltipWidthOrdinal =
            2;

    // Captured Values
    @Unique
    private int scrollableTooltips$tooltipY = 0;
    @Unique
    private int scrollableTooltips$tooltipX = 0;
    @Unique
    private int scrollableTooltips$tooltipHeight = 0;
    @Unique
    private int scrollableTooltips$tooltipWidth = 0;

    // Internal Values
    @Unique
    private final UMatrixStack scrollableTooltips$matrixStack = new UMatrixStack();
    @Unique
    private Slot scrollableTooltips$currentSlot = null;

    @Inject(method = scrollableTooltips$mixinTarget, at = @At("HEAD"))
    private void scrollableTooltips$detectItemChange(CallbackInfo ci) {
        Screen currentScreen = UMinecraft.getMinecraft().screen;
        if (currentScreen instanceof AbstractContainerScreen) {
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
            slice = @Slice(to = @At(value = "INVOKE", target = "Ljava/util/List;size()I")),
            ordinal = scrollableTooltips$tooltipYOrdinal
    )
    private int scrollableTooltips$captureTooltipY(int tooltipY) {
        scrollableTooltips$tooltipY = tooltipY;
        return 0;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/List;size()I")),
            ordinal = scrollableTooltips$tooltipYOrdinal
    )
    private int scrollableTooltips$ignoreTooltipY(int tooltipY) {
        return 0;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            ordinal = scrollableTooltips$tooltipHeightOrdinal
    )
    private int scrollableTooltips$captureTooltipHeight(int tooltipHeight) {
        scrollableTooltips$tooltipHeight = tooltipHeight;
        return tooltipHeight;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            slice = @Slice(to = @At(value = "INVOKE", target = "Ljava/util/List;size()I")),
            ordinal = scrollableTooltips$tooltipXOrdinal
    )
    private int scrollableTooltips$captureTooltipX(int tooltipX) {
        scrollableTooltips$tooltipX = tooltipX;
        return 0;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/List;size()I")),
            ordinal = scrollableTooltips$tooltipXOrdinal
    )
    private int scrollableTooltips$ignoreTooltipX(int tooltipX) {
        return 0;
    }

    @ModifyVariable(
            method = scrollableTooltips$mixinTarget,
            at = @At("STORE"),
            ordinal = scrollableTooltips$tooltipWidthOrdinal
    )
    private int scrollableTooltips$captureTooltipWidth(int tooltipWidth) {
        scrollableTooltips$tooltipWidth = tooltipWidth;
        return tooltipWidth;
    }


    @Inject(
            method = scrollableTooltips$mixinTarget,
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V",
                    shift = At.Shift.AFTER
            )
    )
    private void scrollableTooltips$pushMatrixAndTranslate(PoseStack arg, List<? extends FormattedCharSequence> list, int m, int n,
            //#if FORGE && MC>11600
            Font font,
            //#endif
            CallbackInfo ci) {
        scrollableTooltips$matrixStack.push();

        // Replicate original behavior
        if (scrollableTooltips$tooltipX + scrollableTooltips$tooltipWidth > UScreen.getCurrentScreen().width) {
            scrollableTooltips$tooltipX -= 28 + scrollableTooltips$tooltipWidth;
        }

        if (scrollableTooltips$tooltipY + scrollableTooltips$tooltipHeight + 6 > UScreen.getCurrentScreen().height) {
            scrollableTooltips$tooltipY = UScreen.getCurrentScreen().height - scrollableTooltips$tooltipHeight - 6;
        }

        scrollableTooltips$matrixStack.translate(scrollableTooltips$tooltipX, scrollableTooltips$tooltipY, 0.0);
        GuiUtilsOverride.drawHoveringText(scrollableTooltips$matrixStack, scrollableTooltips$tooltipY, scrollableTooltips$tooltipHeight);
        scrollableTooltips$matrixStack.applyToGlobalState();
    }

    @Inject(
            method = scrollableTooltips$mixinTarget,
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"
            )
    )
    private void scrollableTooltips$popMatrix(PoseStack arg, List<? extends FormattedCharSequence> list, int m, int n,
            //#if FORGE && MC>11600
            Font font,
            //#endif
            CallbackInfo ci) {
        scrollableTooltips$matrixStack.pop();
    }
}
