package com.hojongs.navermapskt.http.client.ktor

import com.hojongs.navermapskt.NaverClientConfig
import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.client.NaverHttpClient
import com.hojongs.navermapskt.reversegc.ReverseGCRequest
import com.hojongs.navermapskt.reversegc.ReverseGCResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * implementation of [NaverHttpClient] with Ktor
 *
 * @property logLevel value of [LogLevel]
 */
class NaverHttpClientKtor(
    private val naverClientConfig: NaverClientConfig,
    private val logLevel: String = "NONE",
) : NaverHttpClient {
    private val ktorClient =
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.valueOf(logLevel)
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                })
            }
            defaultRequest {
                method = HttpMethod.Get
                host = "naveropenapi.apigw.ntruss.com"
                url {
                    protocol = URLProtocol.HTTPS
                }
                header("X-NCP-APIGW-API-KEY-ID", naverClientConfig.clientId)
                header("X-NCP-APIGW-API-KEY", naverClientConfig.clientSecret)
                accept(ContentType.Application.Json)
            }
        }

    override suspend fun geocode(geocodeRequest: GeocodeRequest): Geocode =
        ktorClient.use { client ->
            client.request {
                url.encodedPath = "/map-geocode/v2/geocode"
                parameter("query", geocodeRequest.query)
                parameter("coordinate", geocodeRequest.coordinate)
                parameter("filter", geocodeRequest.filter)
                parameter("page", geocodeRequest.page)
                parameter("count", geocodeRequest.count)
            }
        }

    override suspend fun reverseGeocode(reverseGcRequest: ReverseGCRequest): ReverseGCResponse =
        ktorClient.use { client ->
            client.request {
                url.encodedPath = "/map-reversegeocode/v2/gc"
                parameter("request", reverseGcRequest.request.paramString)
                parameter("coords", "${reverseGcRequest.coordsX},${reverseGcRequest.coordsY}")
                parameter("sourcecrs", reverseGcRequest.sourcecrs.paramString)
                parameter("targetcrs", reverseGcRequest.targetcrs.paramString)
                parameter("orders", reverseGcRequest.ordersParamString())
                parameter("output", reverseGcRequest.output.paramString)
                parameter("callback", reverseGcRequest.callback)
            }
        }
}
