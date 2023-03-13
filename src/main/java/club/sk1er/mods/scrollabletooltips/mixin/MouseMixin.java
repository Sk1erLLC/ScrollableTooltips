package club.sk1er.mods.scrollabletooltips.mixin;

//#if MC>11600
import club.sk1er.mods.scrollabletooltips.GuiUtilsOverride;
import gg.essential.universal.UMinecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDD)Z"), cancellable = true)
    private void scrollableTooltips$captureScroll(long l, double d, double e, CallbackInfo ci) {
        Screen currentScreen = UMinecraft.getMinecraft().screen;
        if (UMinecraft.getMinecraft().player == null) return;
        if (currentScreen instanceof MenuAccess) {
            AccessorContainerScreen containerScreen = (AccessorContainerScreen) currentScreen;
            //#if MC<11700
            ItemStack carriedItem = UMinecraft.getMinecraft().player.inventory.getCarried();
            //#else
            //#if FORGE
            //$$ ItemStack carriedItem = ((MenuAccess<?>) currentScreen).getMenu().getCarried();
            //#else
            //$$ ItemStack carriedItem = ((ScreenHandlerProvider<?>) currentScreen).getScreenHandler().getCursorStack();
            //#endif
            //#endif
            if (carriedItem != null &&
                    !carriedItem.isEmpty() &&
                    containerScreen.getHoveredSlot() != null &&
                    containerScreen.getHoveredSlot().hasItem()) {
                if (GuiUtilsOverride.scroll(e)) ci.cancel();
            }
        }
    }
}
//#endif
