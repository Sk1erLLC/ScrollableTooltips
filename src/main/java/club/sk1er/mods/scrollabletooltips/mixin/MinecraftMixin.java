package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.command.ScrollableTooltipsCommand;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Unique
    private static final String scrollableTooltips$initTarget =
            //#if MC>11600
            "<init>";
            //#else
            //$$ "init";
            //#endif

    @Inject(method = scrollableTooltips$initTarget, at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        EssentialAPI.getCommandRegistry().registerCommand(new ScrollableTooltipsCommand());
    }
}
