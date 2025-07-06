import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.gradleup.shadow")
}

// This module must be compiled with a Java 8 toolchain.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    // Required for the legacy Paper API
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(project(":api"))
    // Depend on the shared code from the common module.
    implementation(project(":core:common"))

    // Depend on the last Paper API version that supports Java 8.
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    // The action bar library for legacy versions.
    implementation("io.github.invvk:actionbar-api:1.4")
}

// Ensure Kotlin also targets Java 8 bytecode.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

// Replace placeholders in the plugin.yml file.
tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

// Package the actionbar library into our plugin JAR and relocate it to prevent conflicts.
tasks.shadowJar {
    relocate("io.github.invvk.actionbar.api", "net.rainbowcreation.multiVersionsPluginExample.libs.actionbarapi")
    /*
    relocations {
        relocation {
            pattern.set("io.github.invvk.actionbar.api")
            relocatedPattern.set("net.rainbowcreation.multiVersionsPluginExample.libs.actionbarapi")
        }
    }
     */
    archiveClassifier.set("legacy") // Append "-legacy" to the final JAR name.
    doLast {
        val targetDir = rootProject.layout.projectDirectory.dir("Target").asFile
        targetDir.mkdirs()

        val outputFile = archiveFile.get().asFile
        val version = project.version
        val renamedJarName = "RainBowCreation-$version-legacy.jar"

        val targetFile = File(targetDir, renamedJarName)

        println("Copying ${outputFile.name} to ${targetFile.absolutePath}")
        outputFile.copyTo(targetFile, overwrite = true)
    }
}
