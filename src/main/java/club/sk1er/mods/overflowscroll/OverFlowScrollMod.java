package club.sk1er.mods.overflowscroll;

import club.sk1er.mods.core.gui.notification.Notifications;
import club.sk1er.mods.overflowscroll.modcore.ModCoreInstaller;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

@Mod(modid = OverFlowScrollMod.MOD_ID, name = OverFlowScrollMod.MOD_NAME, version = OverFlowScrollMod.MOD_VERSION, clientSideOnly = true)
public class OverFlowScrollMod {

    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.2";
    public static final String MOD_NAME = "Text Overflow Scroll";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        if (!ForgeVersion.getVersion().contains("2318")) {
            Notifications.INSTANCE.pushNotification(
                "Outdated Forge",
                "Scrollable Tooltips will not work on anything below Forge build 2318.\nPlease consider updating Forge."
            );
        }
    }
}
