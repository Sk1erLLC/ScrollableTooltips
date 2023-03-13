package club.sk1er.mods.scrollabletooltips;

import gg.essential.universal.UMinecraft;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class Config extends Vigilant {

    @Property(
            name = "Master Toggle", description = "Turns the entire mod on/off.",
            category = "General", subcategory = "General",
            type = PropertyType.SWITCH
    )
    public static boolean masterToggle = true;

    @Property(
            name = "Horizontal Scrolling", description = "Turns horizontal scrolling on/off.",
            category = "General", subcategory = "General",
            type = PropertyType.SWITCH
    )
    public static boolean horizontalScrolling = true;

    @Property(
            name = "Vertical Scrolling", description = "Turns vertical scrolling on/off.",
            category = "General", subcategory = "General",
            type = PropertyType.SWITCH
    )
    public static boolean verticalScrolling = true;

    @Property(
            name = "Tooltip Zooming", description = "Turns zooming on tooltips on/off.",
            category = "General", subcategory = "General",
            type = PropertyType.SWITCH
    )
    public static boolean zoom = true;

    @Property(
            name = "Start at the Top of Tooltips", description = "Changes tooltips to always show the top.",
            category = "General", subcategory = "General",
            type = PropertyType.SWITCH
    )
    public static boolean startAtTop = true;

    public static Config INSTANCE = new Config();

    public Config() {
        super(
                new File(UMinecraft.getMinecraft().gameDirectory, "config/scrollable_tooltips.toml"),
                "Scrollable Tooltips (" + ScrollableTooltips.MOD_VERSION + ")"
        );
        initialize();

        addDependency("horizontalScrolling", "masterToggle");
        addDependency("verticalScrolling", "masterToggle");
        addDependency("zoom", "masterToggle");
        addDependency("startAtTop", "masterToggle");

    }
}
