import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
    id("io.gitlab.arturbosch.detekt") version ("1.17.1")
    id("maven-publish")
}

val githubToken: String = System.getenv("GITHUB_TOKEN")

allprojects {
    group = "com.hojongs"
    version = "0.2.2"

    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            setUrl("https://maven.pkg.github.com/hojongs/naver-maps-kt")
            credentials {
                username = "hojongs"
                password = githubToken
            }
        }
        mavenLocal()
    }
}

val kotestVersion = "4.6.1"

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.serialization")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("maven-publish")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    }

    tasks {
        withType<Test>().configureEach {
            useJUnitPlatform()
            maxParallelForks =
                Runtime.getRuntime()
                    .availableProcessors()
                    .let { it - 1 }
                    .takeIf { it > 0 } ?: 1
            // the maximum number of test classes to execute in a forked test process (JVM)
            setForkEvery(100)

            reports.html.required.set(false)
            reports.junitXml.required.set(false)
        }

        withType<JavaCompile>().configureEach {
            options.apply {
                isFork = true
                setIncremental(true) // default
            }
        }

        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
        }
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                setUrl("https://maven.pkg.github.com/hojongs/naver-maps-kt")
                credentials {
                    username = "hojongs"
                    password = githubToken
                }
            }
        }

        publications {
            create<MavenPublication>("gpr") {
                artifactId = "naver-maps-kt-$artifactId"

                from(components["java"])
            }
        }
    }
}
