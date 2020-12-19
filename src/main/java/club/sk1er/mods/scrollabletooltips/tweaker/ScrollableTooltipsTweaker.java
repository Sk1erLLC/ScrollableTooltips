package club.sk1er.mods.scrollabletooltips.tweaker;


import club.sk1er.mods.scrollabletooltips.forge.FMLLoadingPlugin;
import net.modcore.loader.ModCoreSetupTweaker;

public class ScrollableTooltipsTweaker extends ModCoreSetupTweaker {

    public ScrollableTooltipsTweaker() {
        super(new String[]{FMLLoadingPlugin.class.getName()});
    }
}
