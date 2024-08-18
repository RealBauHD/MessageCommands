plugins {
    id("java")
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":common"))

    implementation(libs.adventure.platform.bungeecord)
    implementation(libs.minimessage)
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
}

tasks.shadowJar {
    relocate("net.kyori", "dev.bauhd.libs.net.kyori")
}