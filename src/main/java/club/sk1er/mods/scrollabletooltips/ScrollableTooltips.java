package club.sk1er.mods.scrollabletooltips;


import club.sk1er.mods.scrollabletooltips.command.ScrollableTooltipsCommand;
import gg.essential.api.EssentialAPI;
//#if FORGE
import net.minecraftforge.fml.common.Mod;
//#if MC>11600
@Mod(ScrollableTooltips.MOD_ID)
//#else
//$$ @Mod(modid = ScrollableTooltips.MOD_ID, name = ScrollableTooltips.MOD_NAME, version = ScrollableTooltips.MOD_VERSION, clientSideOnly = true)
//#endif
//#endif
public class ScrollableTooltips {

    public static final String MOD_ID = "scrollable_tooltips";
    public static final String MOD_VERSION = "1.4.0";
    public static final String MOD_NAME = "Scrollable Tooltips";

    public ScrollableTooltips() {
    }

//    @Mod.EventHandler
//    public void loadComplete(FMLLoadCompleteEvent event) {
//        //noinspection ConstantConditions
////        if (!ForgeVersion.getVersion().contains("2318") && ForgeVersion.mcVersion.equalsIgnoreCase("1.8.9")) {
////            EssentialAPI.getNotifications().push(
////                    "Outdated Forge",
////                    "Scrollable Tooltips will not work on anything below Forge build 2318.\nPlease consider updating Forge."
////            );
////        }
//        if (ModList.get().isLoaded("text_overflow_scroll")) {
//            EssentialAPI.getNotifications().push(
//                    "Duplicate Scrollable Tooltips",
//                    "Scrollable tooltips has detected an older version. \nPlease remove the older version to avoid possible incompatibilities."
//            );
//        }
//    }
}
