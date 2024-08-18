plugins {
    id("java")
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))

    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}