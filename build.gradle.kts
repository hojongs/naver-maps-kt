plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
    id("io.gitlab.arturbosch.detekt") version("1.17.1")
}

group = "com.hojongs.navermapskt"
version = "0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

val kotestVersion = "4.6.1"

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.serialization")
        plugin("io.gitlab.arturbosch.detekt")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }
    }
}
