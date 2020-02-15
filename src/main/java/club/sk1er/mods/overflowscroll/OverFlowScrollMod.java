package club.sk1er.mods.overflowscroll;

import club.sk1er.mods.overflowscroll.modcore.ModCoreInstaller;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = OverFlowScrollMod.MOD_ID, name = OverFlowScrollMod.MOD_NAME, version = OverFlowScrollMod.MOD_VERSION, clientSideOnly = true)
public class OverFlowScrollMod {

    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.0";
    public static final String MOD_NAME = "Text Overflow Scroll";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
    }

}
