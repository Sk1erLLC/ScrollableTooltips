package club.sk1er.mods.scrollabletooltips;

import gg.essential.api.EssentialAPI;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

@Mod(modid = ScrollableTooltips.MOD_ID, name = ScrollableTooltips.MOD_NAME, version = ScrollableTooltips.MOD_VERSION, clientSideOnly = true)
public class ScrollableTooltips {

    // todo: migrate modid to scrollable_tooltips
    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.4.0";
    public static final String MOD_NAME = "Scrollable Tooltips";

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        //noinspection ConstantConditions
        if (!ForgeVersion.getVersion().contains("2318") && ForgeVersion.mcVersion.equalsIgnoreCase("1.8.9")) {
            EssentialAPI.getNotifications().push(
                    "Outdated Forge",
                    "Scrollable Tooltips will not work on anything below Forge build 2318.\nPlease consider updating Forge."
            );
        }
    }
}
