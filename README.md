# Naver Maps Kt

Naver Maps API SDK for Kotlin/JVM

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

## Caution

If 403 Forbidden error occurs, please check the API is enabled in your console.

## TODO

- Add Getting Started Guide
- Publish Package to Maven Repository
- Features
  - Implement more [REST APIs](https://www.ncloud.com/product/applicationService/maps)
    - Static Map
    - Directions
  - Add exception classes
- Build environment
  - Add GitHub Action for CI/CD
  - Test Coverage with Jacoco
  - Refactoring Gradle build script

# Used libraries

- Logging : [Logback](https://github.com/qos-ch/logback)
- HTTP Client : [ktor](https://ktor.io/)
- JSON (De)Serialization : [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
- Test Framework
  - [kotest](https://kotest.io/)
  - [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin)
