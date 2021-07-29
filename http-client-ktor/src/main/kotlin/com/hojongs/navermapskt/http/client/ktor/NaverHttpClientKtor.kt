package com.hojongs.navermapskt.http.client.ktor

import com.hojongs.navermapskt.NaverClientConfig
import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.client.NaverHttpClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

class NaverHttpClientKtor(
    private val naverClientConfig: NaverClientConfig,
) : NaverHttpClient {
    private val ktorClient =
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
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

    /**
     * Append parameter if [value] is not null, else do nothing
     */
    private fun ParametersBuilder.appendOrNone(name: String, value: String?) {
        value?.let { append(name, value) }
    }

    /**
     * Append parameter if [value] is not null, else do nothing
     */
    private fun ParametersBuilder.appendOrNone(name: String, value: Long?) {
        value?.let { append(name, value.toString()) }
    }
}
