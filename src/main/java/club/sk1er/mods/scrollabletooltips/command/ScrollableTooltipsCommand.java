package club.sk1er.mods.scrollabletooltips.command;

import club.sk1er.mods.scrollabletooltips.Config;
import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

public class ScrollableTooltipsCommand extends Command {
    public ScrollableTooltipsCommand() {
        super("scrollabletooltips", true, false);
    }

    @DefaultHandler
    public void handle() {
        EssentialAPI.getGuiUtil().openScreen(Config.INSTANCE.gui());
    }
}
