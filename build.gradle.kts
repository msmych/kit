plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}

val assertjVersion: String by project
val coroutinesVersion: String by project
val i18nVersion: String by project
val junitVersion: String by project
val kotestVersion: String by project
val logbackVersion: String by project
val loggingVersion: String by project
val serializationVersion: String by project
val typesafeConfigVersion: String by project

dependencies {
    api("ch.qos.logback:logback-classic:$logbackVersion")
    api("com.neovisionaries:nv-i18n:$i18nVersion")
    api("com.typesafe:config:$typesafeConfigVersion")
    api("io.github.microutils:kotlin-logging-jvm:$loggingVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

subprojects {
    repositories {
        mavenCentral()
    }
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
                    password = System.getenv("GH_PACKAGES_RW_TOKEN")
                }
            }
        }
    }
}
