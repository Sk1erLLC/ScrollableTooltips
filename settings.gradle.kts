pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://repo.essential.gg/repository/maven-snapshots")
    }
    plugins {
        val egtVersion = "0.1.18+loader-script-SNAPSHOT"
        id("gg.essential.multi-version.root") version egtVersion
    }
}

rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.8.9-forge",
    "1.12.2-forge",
    "1.16.5-forge",
    "1.16.5-fabric",
    "1.17.1-fabric",
    "1.17.1-forge",
    "1.18.1-fabric",
    "1.18.2-fabric",
    "1.18.2-forge",
    "1.19-fabric",
    "1.19.2-fabric",
    "1.19.2-forge",
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle.kts"
    }

    include(":api:$version")
    project(":api:$version").apply {
        projectDir = file("versions/api/$version")
        buildFileName = "../../../api/build.gradle.kts"
    }
}