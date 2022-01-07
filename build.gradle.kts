java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

// Project metadata

group = "land.altea"
description = "MySQL-powered allowlists for your server."
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    compileOnly("org.jetbrains", "annotations", "23.0.0")
    compileOnly("org.spigotmc", "spigot-api", "1.18.1-R0.1-SNAPSHOT")
}

// ShadowJAR configuration and tasks

tasks.jar.get().enabled = false // do not create regular jar

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    configurations = mutableListOf(project.configurations.shadow.get())
    archiveClassifier.set(null as String?)
}

tasks.register<com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation>("autoRelocateDependencies") {
    target = tasks.shadowJar.get()
    prefix = "land.altea.allowdb.dependency"
}

tasks.shadowJar.get().dependsOn(tasks["autoRelocateDependencies"])