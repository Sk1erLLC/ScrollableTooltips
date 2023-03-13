import gg.essential.gradle.util.versionFromBuildIdAndBranch

plugins {
    kotlin("jvm") version "1.6.0" apply false
    id("gg.essential.multi-version.root")
}

version = versionFromBuildIdAndBranch()

preprocess {
    val forge11605 = createNode("1.16.5-forge", 11605, "srg")

    // legacy forge
    val forge11202 = createNode("1.12.2-forge", 11202, "srg")
    val forge10809 = createNode("1.8.9-forge", 10809, "srg")

    forge11202.link(forge11605, file("versions/1.16.5-1.12.2.txt"))
    forge10809.link(forge11202)

    // Fabric
    val fabric11902 = createNode("1.19.2-fabric", 11902, "yarn")
    val fabric11900 = createNode("1.19-fabric", 11900, "yarn")
    val fabric11802 = createNode("1.18.2-fabric", 11802, "yarn")
    val fabric11801 = createNode("1.18.1-fabric", 11801, "yarn")
    val fabric11701 = createNode("1.17.1-fabric", 11701, "yarn")
    val fabric11605 = createNode("1.16.5-fabric", 11605, "yarn")

    fabric11605.link(forge11605)
    fabric11701.link(fabric11605)
    fabric11801.link(fabric11701)
    fabric11802.link(fabric11801)
    fabric11900.link(fabric11802)
    fabric11902.link(fabric11900)

    // Modern Forge
    val forge11902 = createNode("1.19.2-forge", 11902, "srg")
    val forge11802 = createNode("1.18.2-forge", 11802, "srg")
    val forge11701 = createNode("1.17.1-forge", 11701, "srg")

    forge11701.link(forge11605)
    forge11802.link(forge11701)
    forge11902.link(forge11802)



}