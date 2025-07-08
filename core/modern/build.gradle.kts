import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.xpdustry.ksr.kotlinRelocate

plugins {
    id("com.gradleup.shadow")
    //id("com.github.johnrengelman.shadow")
    id("com.xpdustry.kotlin-shadow-relocator")
    id("org.jetbrains.kotlin.jvm")
}

// This module must be compiled with a Java 21 toolchain for the modern API.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    // Required for the modern Paper API
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.clojars.org")
}

dependencies {
    // Depend on the shared code from the common module.
    api(project(":api"))
    implementation(project(":core:common"))

    // Depend on the latest Paper API, which includes Folia support.
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    // Lib for Multipaper and ShreddedPaper
    implementation("com.github.puregero:multilib:1.2.4")
}

// Align the Kotlin compiler's target to Java 21.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

// Replace placeholders in the plugin.yml file.
tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.jar {
    enabled = false
}


// Configure the final JAR output.
tasks.shadowJar {
    kotlinRelocate("com.github.puregero.multilib", "net.rainbowcreation.libs.multilib")
    //archiveClassifier.set("")  // no classifier; overwrite default jar
    // Append "-modern" to the final JAR name for clarity.
    archiveClassifier.set("compile")
    doLast {
        val targetDir = rootProject.layout.projectDirectory.dir("Target").asFile
        targetDir.mkdirs()

        val outputFile = archiveFile.get().asFile
        val version = project.version
        val renamedJarName = "RainBowCreation-$version-modern.jar"

        val targetFile = File(targetDir, renamedJarName)

        println("Copying ${outputFile.name} to ${targetFile.absolutePath}")
        outputFile.copyTo(targetFile, overwrite = true)
    }
}

// Create a shareable "bucket" for our shadow jar
val shadowJarOutput by configurations.creating

// Attach the output of the shadowJar task to this bucket
artifacts {
    // The correct syntax is: add("configurationName", task)
    add("shadowJarOutput", tasks.shadowJar)
}
