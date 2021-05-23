package club.sk1er.mods.scrollabletooltips.tweaker;

import club.sk1er.mods.scrollabletooltips.forge.FMLLoadingPlugin;
import gg.essential.loader.EssentialTweaker;

@SuppressWarnings("unused")
public class ScrollableTooltipsTweaker extends EssentialTweaker {
    public ScrollableTooltipsTweaker() {
        super(new String[]{FMLLoadingPlugin.class.getName()});
    }
}
