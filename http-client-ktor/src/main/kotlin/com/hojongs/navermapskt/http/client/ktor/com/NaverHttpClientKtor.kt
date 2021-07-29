package com.hojongs.navermapskt.http.client.ktor.com

import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.NaverHttpClient
import com.hojongs.navermapskt.NaverClientConfig
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

class NaverHttpClientKtor(
    override val naverClientConfig: NaverClientConfig,
) : NaverHttpClient() {
    //    private val ktorClient = HttpClient(CIO)
    private val ktorClient =
        HttpClient() {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                })
            }
            defaultRequest {
                header("X-NCP-APIGW-API-KEY-ID", naverClientConfig.clientId)
                header("X-NCP-APIGW-API-KEY", naverClientConfig.clientSecret)
            }
        }

    override suspend fun geocode(geocodeRequest: GeocodeRequest): Geocode =
        ktorClient.use { client ->
            client.request {
                method = HttpMethod.Get
                url {
                    protocol = URLProtocol.HTTPS
                    host = "naveropenapi.apigw.ntruss.com"
                    encodedPath = "/map-geocode/v2/geocode"
                    parameters.apply {
                        geocodeRequest.let { request ->
                            append("query", request.query)
                            appendOrNone("coordinate", request.coordinate)
                            appendOrNone("filter", request.filter)
                            appendOrNone("page", request.page)
                            appendOrNone("count", request.count)
                        }
                    }
                }
            }
        }

    private fun ParametersBuilder.appendOrNone(name: String, value: String?) {
        value?.let { append(name, value) }
    }

    private fun ParametersBuilder.appendOrNone(name: String, value: Long?) {
        value?.let { append(name, value.toString()) }
    }
}