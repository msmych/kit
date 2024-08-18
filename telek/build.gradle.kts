plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

val assertjVersion: String by project
val junitVersion: String by project
val coroutinesVersion: String by project

dependencies {
    implementation(project(":"))
    implementation(project(":voron"))

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}