package club.sk1er.mods.scrollabletooltips.command;

import club.sk1er.mods.scrollabletooltips.Config;
import club.sk1er.mods.scrollabletooltips.ScrollableTooltips;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ScrollableTooltipsCommand extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "scrollabletooltips";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "scrollabletooltips";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        ScrollableTooltips.displayScreen = Config.INSTANCE.gui();
    }
}
