plugins {
    id("java")
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":common"))

    implementation(libs.adventure.platform.bukkit)
    implementation(libs.minimessage)
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

tasks.shadowJar {
    relocate("net.kyori", "dev.bauhd.libs.net.kyori")
}