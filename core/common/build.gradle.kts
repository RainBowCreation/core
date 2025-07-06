import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // This is a standard library module, not a runnable plugin,
    // so it doesn't need the shadow plugin.
    `java-library`
}

// All code in this module will be compatible with Java 8.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    // We need a repository for the Spigot API dependency.
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    // Make our project's API available to this module.
    implementation(project(":api"))

    // Depend on a very old Spigot API. This provides access to common classes
    // like Player, CommandSender, etc., without tying us to a specific version.
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    // The Adventure API is safe to include for modern text components.
    compileOnly("net.kyori:adventure-api:4.17.0")

    // Add the HikariCP dependency for our data source.
    implementation("com.zaxxer:HikariCP:5.1.0")
}

// Ensure the Kotlin compiler also targets Java 8 bytecode.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}
