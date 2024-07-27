plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}

val assertjVersion: String by project
val coroutinesVersion: String by project
val jupiterVersion: String by project
val logbackVersion: String by project
val loggingVersion: String by project
val mockkVersion: String by project
val serializationVersion: String by project

dependencies {
    api("ch.qos.logback:logback-classic:$logbackVersion")
    api("io.github.microutils:kotlin-logging-jvm:$loggingVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    testApi("io.mockk:mockk:$mockkVersion")
    testApi("org.assertj:assertj-core:$assertjVersion")
    testApi("org.junit.jupiter:junit-jupiter-api")
    testApi("org.junit.jupiter:junit-jupiter-engine")
    testApi(platform("org.junit:junit-bom:$jupiterVersion"))
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "uk.matvey"
            artifactId = "kit"
            version = project.findProperty("releaseVersion") as? String ?: "0.1.0-SNAPSHOT"

            from(components["java"])

            pom {
                name = "Kit"
                description = "Kotlin utilities"
                url = "https://github.com/msmych/kit"

                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://spdx.org/licenses/Apache-2.0.html"
                    }
                }
                developers {
                    developer {
                        id = "msmych"
                        name = "Matvey Smychkov"
                        email = "realsmych@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/msmych/kit.git"
                    developerConnection = "scm:git:ssh://github.com/msmych/kit.git"
                    url = "https://github.com/msmych/kit"
                }
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/msmych/kit")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GH_TOKEN")
                }
            }
        }
    }
}
