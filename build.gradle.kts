plugins {
    kotlin("jvm") version "1.9.22"
}

group = "xyz.stellar"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()

    maven {
        name = "Sonatype Snapshots (Legacy)"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        name = "Sonatype Snapshots"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation("dev.kord:kord-core:0.14.0")
    implementation("com.kotlindiscord.kord.extensions:kord-extensions:1.9.0-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.reflections:reflections:0.9.12")
    implementation("io.github.oshai:kotlin-logging:6.0.3")
    implementation("ch.qos.logback:logback-classic:1.5.0")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.0")
}

kotlin {
    jvmToolchain(17)
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "xyz.stellar.felicity.MainKt"
        )
    }
}