package club.sk1er.mods.scrollabletooltips;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.modcore.api.ModCoreAPI;

@Mod(modid = ScrollableTooltips.MOD_ID, name = ScrollableTooltips.MOD_NAME, version = ScrollableTooltips.MOD_VERSION, clientSideOnly = true)
public class ScrollableTooltips {

    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.3";
    public static final String MOD_NAME = "Text Overflow Scroll";


    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        if (!ForgeVersion.getVersion().contains("2318") && ForgeVersion.mcVersion.equalsIgnoreCase("1.8.9")) { //Not always true due to other vers
            ModCoreAPI.getNotifications().push(
                    "Outdated Forge",
                    "Scrollable Tooltips will not work on anything below Forge build 2318.\nPlease consider updating Forge."
            );
        }
    }
}
