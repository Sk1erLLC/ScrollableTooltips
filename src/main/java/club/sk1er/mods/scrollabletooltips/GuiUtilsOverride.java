package club.sk1er.mods.scrollabletooltips;

import gg.essential.universal.UKeyboard;
import gg.essential.universal.UMatrixStack;
import org.lwjgl.input.Mouse;

import java.util.List;

@SuppressWarnings("unused")
public class GuiUtilsOverride {
    public static int scrollY = 0;
    public static boolean allowScrolling;
    public static int scrollX = 0;
    public static double zoomFactor = 1.0;

    /**
     * Draws a tooltip box on the screen with text in it.
     * Automatically positions the box relative to the mouse to match Mojang's implementation.
     * Automatically wraps text when there is not enough space on the screen to display the text without wrapping.
     * Can have a maximum width set to avoid creating very wide tooltips.
     *
     * @param textLines    the lines of text to be drawn in a hovering tooltip box.
     * @param screenHeight the available  screen height for the tooltip to drawn in
     */
    public static void drawHoveringText(UMatrixStack matrixStack, List<String> textLines, int screenHeight, int tooltipY, int tooltipHeight) {
        if (!allowScrolling) {
            scrollX = 0;
            if (tooltipY < 0) {
                scrollY = -tooltipY + 6;
            } else {
                scrollY = 0;
            }
            zoomFactor = 1.0;
        }

        if (!Config.masterToggle) return;
        allowScrolling = tooltipY < 0;
        if (allowScrolling) {
            int eventDWheel = Mouse.getDWheel();
            if (UKeyboard.isCtrlKeyDown()) {
                zoomFactor *= (1.0 + 0.1 * Integer.signum(eventDWheel));
            } else if (UKeyboard.isShiftKeyDown() && Config.horizontalScrolling) {
                if (eventDWheel < 0) {
                    scrollX += 10;
                } else if (eventDWheel > 0) {
                    //Scrolling to access higher stuff
                    scrollX -= 10;
                }
            } else if (Config.verticalScrolling) {
                if (eventDWheel < 0) {
                    scrollY -= 10;
                } else if (eventDWheel > 0) {
                    //Scrolling to access higher stuff
                    scrollY += 10;
                }
            }

            if (scrollY + tooltipY > 6) {
                scrollY = -tooltipY + 6;
            } else if (scrollY + tooltipY + tooltipHeight + 6 < screenHeight) {
                scrollY = screenHeight - 6 - tooltipY - tooltipHeight;
            }
        }

        matrixStack.translate(scrollX, scrollY, 0);
        matrixStack.scale(zoomFactor, zoomFactor, 0.0);
        matrixStack.applyToGlobalState();
    }
}
