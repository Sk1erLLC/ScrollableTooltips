package club.sk1er.mods.scrollabletooltips;

import gg.essential.universal.UKeyboard;
import gg.essential.universal.UMatrixStack;
import gg.essential.universal.UScreen;

@SuppressWarnings("unused")
public class GuiUtilsOverride {
    public static boolean renderingTooltip = false;
    public static int scrollY = 0;
    public static boolean needsReset;
    public static boolean allowScrolling;
    public static int scrollX = 0;
    public static double zoomFactor = 1.0;

    public static void drawHoveringText(UMatrixStack matrixStack, int tooltipY, int tooltipHeight) {
        assert UScreen.getCurrentScreen() != null;


        if (needsReset) {
            scrollX = 0;
            allowScrolling =
                    // Check if the tooltip is larger than the screen
                    (tooltipHeight + 12) > UScreen.getCurrentScreen().height ||
                    // Also check if the tooltip goes off-screen
                    Math.abs(tooltipY) + tooltipHeight > UScreen.getCurrentScreen().height;
            if (allowScrolling && Config.startAtTop) {
                scrollY = 6 - tooltipY;
            } else {
                scrollY = 0;
            }
            zoomFactor = 1.0;
            needsReset = false;

        }

        if (!Config.masterToggle) return;

        //FIXME: Causes Flickering
//        if (allowScrolling) {
//            int screenHeight = UScreen.getCurrentScreen().height;
//            int max = 6 - tooltipY;
//            int min = screenHeight - tooltipY - tooltipHeight - 6;
//            if (scrollY > max) {
//                scrollY = max;
//            } else if (scrollY < min) {
//                scrollY = min;
//            }
//        }

        matrixStack.translate(scrollX, scrollY, 0);
        matrixStack.scale(zoomFactor, zoomFactor, 1.0);
    }

    public static void resetScroll() {
        needsReset = true;
        allowScrolling = false;
    }

    public static boolean scroll(double delta) {
        if (allowScrolling) {
            if (UKeyboard.isCtrlKeyDown() && Config.zoom) {
                zoomFactor *= (1.0 + 0.1 * Math.signum(delta));
                return true;
            } else if (UKeyboard.isShiftKeyDown() && Config.horizontalScrolling) {
                scrollX += 10 * Math.signum(delta);
                return true;
            } else if (Config.verticalScrolling) {
                scrollY += 10 * Math.signum(delta);
                return true;
            }
        }
        return false;
    }
}
