plugins {
    kotlin("jvm") version "1.5.21"
}

group = "com.hojongs"
version = "0.1"

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        setUrl("https://maven.pkg.github.com/hojongs/naver-maps-kt")
    }
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.hojongs:naver-maps-kt-core:0.1")
    implementation("com.hojongs:naver-maps-kt-http-client-ktor:0.1")
}
