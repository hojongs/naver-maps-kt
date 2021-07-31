package com.hojongs.navermapskt.example

import com.hojongs.navermapskt.NaverClientConfig
import com.hojongs.navermapskt.NaverMapsService
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.client.ktor.NaverHttpClientKtor
import com.hojongs.navermapskt.reversegc.ReverseGCRequest

suspend fun main() {
    val config = NaverClientConfig(
        System.getenv("NAVER_MAPS_CLIENT_ID"),
        System.getenv("NAVER_MAPS_CLIENT_SECRET"),
    )
    val client = NaverHttpClientKtor(config)
    val naverMapsService = NaverMapsService(client)

    val geocode = naverMapsService.geocode(GeocodeRequest("분당구 불정로 6"))
    println(geocode)

    val reverseGcResponse = naverMapsService.reverseGeocode(
        ReverseGCRequest(
            "129.1133567",
            "35.2982640",
            output = ReverseGCRequest.Output.JSON,
        )
    )
    println(reverseGcResponse)

    client.close()
}
