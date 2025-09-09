plugins {
    id("java")
}

group = "dev.bauhd.messagecommands"
version = "3.1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }
}