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
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    compileOnly("net.md-5:bungeecord-api:1.21-R0.1-SNAPSHOT")
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
}