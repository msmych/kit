plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

dependencies {
    api("io.ktor:ktor-server-core")
    api("io.ktor:ktor-server-netty")
    api("io.ktor:ktor-server-freemarker")
}
