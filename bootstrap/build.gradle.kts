import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy

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

plugins {
    `java-library`
    id("com.gradleup.shadow")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.clojars.org")
}

dependencies {
    api(project(":api"))
    implementation(project(":core:common"))
    implementation(project(":core:legacy"))
    implementation(project(":core:modern"))

    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
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
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier.set("") // no "-all" suffix, it's the main jar

    relocate("io.github.invvk.actionbar.api", "net.rainbowcreation.libs.actionbarapi")
    relocate("com.github.puregero.multilib", "net.rainbowcreation.libs.multilib")

    // ✅ include plugin.yml manually just to be safe
    from("src/main/resources") {
        include("plugin.yml")
    }

    from("${layout.buildDirectory}/generated-resources/configs") {
        include("legacy-config.yml", "modern-config.yml")
    }

    from("${layout.buildDirectory}/generated-resources/plugin-ymls") {
        include("plugin-modern.yml", "plugin-legacy.yml")
    }

    doLast {
        val targetDir = rootProject.layout.projectDirectory.dir("Target").asFile
        targetDir.mkdirs()

        val outputFile = archiveFile.get().asFile
        val version = project.version
        val renamedJarName = "RainBowCreation-$version.jar"
        val renamedLatestJarName = "RainBowCreation.jar"

        val targetFile = File(targetDir, renamedJarName)

        println("Copying ${outputFile.name} to ${targetFile.absolutePath}")
        outputFile.copyTo(targetFile, overwrite = true)

        val targetLatestFile = File(targetDir, renamedLatestJarName)

        println("Copying ${outputFile.name} to ${targetLatestFile.absolutePath}")
        outputFile.copyTo(targetLatestFile, overwrite = true)
    }
}
