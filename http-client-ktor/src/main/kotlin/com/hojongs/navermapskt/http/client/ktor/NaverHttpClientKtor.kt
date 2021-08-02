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
        get(
            "/map-geocode/v2/geocode",
            listOf(
                "query" to geocodeRequest.query,
                "coordinate" to geocodeRequest.coordinate,
                "filter" to geocodeRequest.filter,
                "page" to geocodeRequest.page,
                "count" to geocodeRequest.count,
            )
        )

    override suspend fun reverseGeocode(reverseGcRequest: ReverseGCRequest): ReverseGCResponse =
        get(
            "/map-reversegeocode/v2/gc",
            listOf(
                "request" to reverseGcRequest.request.paramString,
                "coords" to "${reverseGcRequest.coordsX},${reverseGcRequest.coordsY}",
                "sourcecrs" to reverseGcRequest.sourcecrs.paramString,
                "targetcrs" to reverseGcRequest.targetcrs.paramString,
                "orders" to reverseGcRequest.ordersParamString(),
                "output" to reverseGcRequest.output.paramString,
                "callback" to reverseGcRequest.callback,
            )
        )

    private suspend inline fun <reified T> get(encodedPath: String, params: List<Pair<String, Any?>>): T =
        ktorClient.get {
            url.encodedPath = encodedPath
            params.forEach { (k, v) -> parameter(k, v) }
        }

    override fun close() {
        ktorClient.close()
    }
}
