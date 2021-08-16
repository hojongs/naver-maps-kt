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
    mavenLocal()
}

val naverMapsKtVersion = "0.2.2"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.hojongs:naver-maps-kt-core:$naverMapsKtVersion")
    implementation("com.hojongs:naver-maps-kt-http-client-ktor:$naverMapsKtVersion")
}
