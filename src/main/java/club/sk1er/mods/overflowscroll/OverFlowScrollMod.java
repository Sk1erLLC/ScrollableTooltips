package club.sk1er.mods.overflowscroll;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = OverFlowScrollMod.MOD_ID, name = OverFlowScrollMod.MOD_NAME, version = OverFlowScrollMod.MOD_VERSION, clientSideOnly = true)
public class OverFlowScrollMod {

    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.0";
    public static final String MOD_NAME = "Text Overflow Scroll";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

}
