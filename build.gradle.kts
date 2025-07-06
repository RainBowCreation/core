plugins {
    // These plugins are not automatically applied.
    // They can be applied in subprojects as needed (in their respective build files).
    id("org.jetbrains.kotlin.jvm") version "2.2.0" apply false
    id("com.gradleup.shadow") version "9.0.0-rc1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0" apply false
}
// Configure shared settings for all sub-projects.
subprojects {
    group = "net.rainbowcreation.core"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java-library")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
