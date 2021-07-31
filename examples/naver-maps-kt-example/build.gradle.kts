plugins {
    kotlin("jvm") version "1.5.21"
}

val githubToken: String = System.getenv("GITHUB_TOKEN")

group = "com.hojongs"
version = "0.1"

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
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.hojongs:naver-maps-kt-core:0.1")
    implementation("com.hojongs:naver-maps-kt-http-client-ktor:0.1")
}
