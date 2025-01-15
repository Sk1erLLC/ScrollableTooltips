package club.sk1er.mods.scrollabletooltips;

import club.sk1er.mods.scrollabletooltips.command.ScrollableTooltipsCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = ScrollableTooltips.MOD_ID, name = ScrollableTooltips.MOD_NAME, version = ScrollableTooltips.MOD_VERSION, clientSideOnly = true)
public class ScrollableTooltips {

    // todo: migrate modid to scrollable_tooltips
    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.4.0";
    public static final String MOD_NAME = "Scrollable Tooltips";

    public static GuiScreen displayScreen = null;

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        ClientCommandHandler.instance.registerCommand(new ScrollableTooltipsCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (displayScreen == null) return;
        Minecraft.getMinecraft().displayGuiScreen(displayScreen);
        displayScreen = null;
    }

    public void joinWorld(WorldEvent.Load event) {
        //noinspection ConstantConditions
        if (!ForgeVersion.getVersion().contains("2318") && ForgeVersion.mcVersion.equalsIgnoreCase("1.8.9")) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("Outdated Forge");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("Scrollable Tooltips will not work on anything below Forge build 2318.\nPlease update Forge.");
        }
    }
}
