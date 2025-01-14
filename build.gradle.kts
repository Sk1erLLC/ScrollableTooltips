import gg.essential.gradle.util.noServerRunConfigs

plugins {
    kotlin("jvm")
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}

val modGroup: String by project
val modBaseName: String by project
group = modGroup
base.archivesName.set("$modBaseName-${platform.mcVersionStr}")

loom {
    noServerRunConfigs()
    mixin {
        defaultRefmapName.set("mixins.scrollabletooltips.refmap.json")
    }
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "org.spongepowered.asm.launch.MixinTweaker")
            arg("--mixin", "mixins.scrollabletooltips.json")
        }
    }
}

repositories {
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

val embed by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    embed("org.spongepowered:mixin:0.7-SNAPSHOT")
    embed("gg.essential:vigilance:306")
    embed(modImplementation("gg.essential:universalcraft-1.8.9-forge:369")!!)
}

tasks.jar {
    from(embed.files.map { zipTree(it) })

    manifest.attributes(
        mapOf(
            "ModSide" to "CLIENT",
            "FMLCorePluginContainsFMLMod" to "Yes, yes it does",
            "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
            "TweakOrder" to "0"
        )
    )
}