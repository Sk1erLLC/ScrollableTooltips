package club.sk1er.mods.scrollabletooltips.tweaker;

import club.sk1er.mods.scrollabletooltips.forge.FMLLoadingPlugin;
import gg.essential.loader.EssentialSetupTweaker;

@SuppressWarnings("unused")
public class ScrollableTooltipsTweaker extends EssentialSetupTweaker {
    public ScrollableTooltipsTweaker() {
        super(new String[]{FMLLoadingPlugin.class.getName()});
    }
}
