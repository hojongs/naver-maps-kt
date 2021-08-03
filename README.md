# Naver Maps Kt

[Naver Maps API](https://www.ncloud.com/product/applicationService/maps) SDK for Kotlin/JVM, Java, Android written by Kotlin

## Getting Started

```kotlin
val config = NaverClientConfig(
    System.getenv("NAVER_MAPS_CLIENT_ID"),
    System.getenv("NAVER_MAPS_CLIENT_SECRET"),
)
val client = NaverHttpClientKtor(config)
val naverMapsService = NaverMapsService(client)

val geocode = client.geocode(GeocodeRequest("분당구 불정로 6"))
val reverseGcResponse = client.reverseGeocode(
    ReverseGCRequest(
        "129.1133567",
        "35.2982640",
        output = ReverseGCRequest.Output.JSON,
    )
)
```

## How to install package

```kotlin
val githubToken: String = System.getenv("GITHUB_TOKEN")

repositories {
    maven {
        name = "GitHubPackages"
        setUrl("https://maven.pkg.github.com/hojongs/naver-maps-kt")
        credentials {
            username = "hojongs" // your GitHub username
            password = githubToken // your Personal Access Token with enough permissions
        }
    }
}

dependencies {
    implementation("com.hojongs:naver-maps-kt-core:0.2")
    implementation("com.hojongs:naver-maps-kt-http-client-ktor:0.2")
}
```

For more information about installing package from Github Package Registry, please refer https://docs.github.com/en/packages/learn-github-packages/installing-a-package

## Caution

If 403 Forbidden error occurs, please check the API is enabled in your console.

## TODO

- Features
  - Implement more [REST APIs](https://www.ncloud.com/product/applicationService/maps)
    - Static Map
    - Directions
  - Add exception classes
- Build environment
  - Add GitHub Action for CI/CD
  - Test Coverage with Jacoco
  - Refactoring Gradle build script

# Dependencies

- Kotlin 1.5.21
- Logging : [Logback](https://github.com/qos-ch/logback)
- HTTP Client : [ktor](https://ktor.io/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- JSON (De)Serialization : [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
- Test Framework
  - [kotest](https://kotest.io/)
  - [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin)

## Reference

This repository follows:

- [semver](https://semver.org/)
