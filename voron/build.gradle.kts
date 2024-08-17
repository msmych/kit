plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

val assertjVersion: String by project
val junitVersion: String by project

dependencies {
    api("io.ktor:ktor-server-core")
    api("io.ktor:ktor-server-netty")
    api("io.ktor:ktor-server-freemarker")
    api("io.ktor:ktor-client-core")
    api("io.ktor:ktor-client-cio")

    implementation(project(":"))

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}

tasks.shadowJar {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "uk.matvey"
            artifactId = "voron"
            version = project.findProperty("releaseVersion") as? String ?: "0.1.0-SNAPSHOT"

            from(components["java"])

            pom {
                name = "Voron"
                description = "Ktor utilities"
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
