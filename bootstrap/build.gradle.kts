import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy

plugins {
    `java-library`
    id("com.gradleup.shadow")
    id("com.xpdustry.kotlin-shadow-relocator")
    id("org.jetbrains.kotlin.jvm")
}

// Define a task to copy and rename legacy config.yml
val copyLegacyConfig by tasks.registering(Copy::class) {
    from("../core/legacy/src/main/resources/config.yml") // path to legacy config
    into("${layout.buildDirectory}/generated-resources/configs") // temp output folder
    rename { "legacy-config.yml" }
}

// Define a task to copy and rename modern config.yml
val copyModernConfig by tasks.registering(Copy::class) {
    from("../core/modern/src/main/resources/config.yml") // path to modern config
    into("${layout.buildDirectory}/generated-resources/configs")
    rename { "modern-config.yml" }
}

val copyPluginYmls by tasks.registering(Copy::class) {
    from("../core/modern/src/main/resources/plugin.yml") {
        rename { "plugin-modern.yml" }
    }
    from("../core/legacy/src/main/resources/plugin.yml") {
        rename { "plugin-legacy.yml" }
    }
    into("${layout.buildDirectory}/generated-resources/plugin-ymls")
}

val jarsToCopy by configurations.creating {
    isCanBeResolved = true
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(8)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
    }
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.clojars.org")
}

dependencies {
    api(project(":api"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    jarsToCopy(project(path = ":core:legacy", configuration = "shadowJarOutput"))
    jarsToCopy(project(path = ":core:modern", configuration = "shadowJarOutput"))
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    dependsOn(copyLegacyConfig, copyModernConfig, copyPluginYmls)

    from("src/main/resources") {
        include("plugin.yml")
    }

    from("${layout.buildDirectory}/generated-resources/configs") {
        into("")
    }

    from("${layout.buildDirectory}/generated-resources/plugin-ymls") {
        into("")
    }

    from(jarsToCopy) {
        include("*legacy*-compile.jar")
        rename { "legacy.jar" }
        into("net/rainbowcreation/core/modules/legacy")
    }

    from(jarsToCopy) {
        include("*modern*-compile.jar")
        rename { "modern.jar" }
        into("net/rainbowcreation/core/modules/modern")
    }
}

tasks.shadowJar {
    // Avoid errors if duplicates occur
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Configure output jar name
    archiveBaseName.set("RainBowCreation-bootstrap")
    archiveVersion.set(project.version.toString())
    archiveClassifier.set("") // no classifier or set if you want

    // After building, copy jar to Target folder with nice name
    doLast {
        val targetDir = rootProject.layout.projectDirectory.dir("Target").asFile
        targetDir.mkdirs()

        val outputFile = archiveFile.get().asFile
        val targetFile = File(targetDir, "RainBowCreation-${project.version}-bootstrap.jar")

        println("Copying ${outputFile.name} to ${targetFile.absolutePath}")
        outputFile.copyTo(targetFile, overwrite = true)

        val targetLatestFile = File(targetDir, "RainBowCreation.jar")

        println("Copying ${outputFile.name} to ${targetLatestFile.absolutePath}")
        outputFile.copyTo(targetLatestFile, overwrite = true)
    }
}