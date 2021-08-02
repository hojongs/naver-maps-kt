package com.hojongs.navermapskt.http.client.ktor

import com.hojongs.navermapskt.NaverClientConfig
import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.client.NaverHttpClient
import com.hojongs.navermapskt.reversegc.ReverseGCRequest
import com.hojongs.navermapskt.reversegc.ReverseGCResponse
import com.hojongs.navermapskt.staticmap.StaticMapRequest
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
                "request" to reverseGcRequest.request.paramValue,
                "coords" to "${reverseGcRequest.coordsX},${reverseGcRequest.coordsY}",
                "sourcecrs" to reverseGcRequest.sourcecrs.paramValue,
                "targetcrs" to reverseGcRequest.targetcrs.paramValue,
                "orders" to reverseGcRequest.ordersParamValue(),
                "output" to reverseGcRequest.output.paramValue,
                "callback" to reverseGcRequest.callback,
            )
        )

    override suspend fun staticMap(staticMapRequest: StaticMapRequest): ByteArray =
        get(
            "/map-static/v2/raster",
            listOf(
                "crs" to staticMapRequest.crs.paramValue,
                "w" to staticMapRequest.w,
                "h" to staticMapRequest.h,
                "maptype" to staticMapRequest.mapType.paramValue,
                "format" to staticMapRequest.format.paramValue,
                "scale" to staticMapRequest.scale.paramValue,
                "lang" to staticMapRequest.lang.paramValue,
                "public_transit" to staticMapRequest.publicTransit,
                "dataversion" to staticMapRequest.dataversion,
            ) + staticMapRequest.centerOrMarkers.toParamList()
        )

    private suspend inline fun <reified T> get(encodedPath: String, params: List<Pair<String, Any?>>): T =
        ktorClient.get {
            url.encodedPath = encodedPath
            params.forEach { (k, v) -> parameter(k, v) }
        }

    private fun StaticMapRequest.CenterOrMarkers.toParamList() =
        when (this) {
            is StaticMapRequest.CenterOrMarkers.Center ->
                listOf(
                    "center" to this.center,
                    "level" to this.level,
                )
            is StaticMapRequest.CenterOrMarkers.Markers ->
                listOf("markers" to this.markers)
        }

    override fun close() {
        ktorClient.close()
    }
}
