plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}

dependencies {
    implementation(project(":"))
    implementation(project(":telek"))
}
