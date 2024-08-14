plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

val junitVersion: String by project

dependencies {
    api("io.ktor:ktor-server-core")
    api("io.ktor:ktor-server-netty")
    api("io.ktor:ktor-server-freemarker")

    implementation(project(":"))

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
