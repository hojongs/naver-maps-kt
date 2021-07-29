package com.hojongs.navermapskt.http.client.ktor.com

import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.NaverHttpClient
import com.hojongs.navermapskt.NaverClientConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLProtocol.Companion.HTTPS
import kotlinx.serialization.json.Json

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
                serializer = KotlinxSerializer(Json {
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
                method = Get
                url {
                    protocol = HTTPS
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
