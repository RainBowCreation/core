import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.gradleup.shadow")
}

// Set the toolchain to Java 17, as required by the Velocity API.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":api"))
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("net.md-5:bungeecord-api:1.21-R0.3-SNAPSHOT")
}

// Set the Kotlin compiler to target Java 17 to match the toolchain.
// The complex '-Xjdk-release' flag is no longer needed here.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.shadowJar {
    archiveClassifier.set("proxies")
    doLast {
        val targetDir = rootProject.layout.projectDirectory.dir("Target").asFile
        targetDir.mkdirs()

        val outputFile = archiveFile.get().asFile
        val version = project.version
        val renamedJarName = "RainBowCreation-$version-proxies.jar"

        val targetFile = File(targetDir, renamedJarName)

        println("Copying ${outputFile.name} to ${targetFile.absolutePath}")
        outputFile.copyTo(targetFile, overwrite = true)

        val renamedLatestJarName = "RainBowCreation-proxies.jar"

        val targetLatestFile = File(targetDir, renamedLatestJarName)

        println("Copying ${outputFile.name} to ${targetLatestFile.absolutePath}")
        outputFile.copyTo(targetLatestFile, overwrite = true)
    }
}
