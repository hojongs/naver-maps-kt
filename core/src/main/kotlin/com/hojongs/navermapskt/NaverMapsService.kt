package com.hojongs.navermapskt

import com.hojongs.navermapskt.http.NaverHttpClient

class NaverMapsService(
    private val naverHttpClient: NaverHttpClient,
) {
    suspend fun geocode(geocodeRequest: GeocodeRequest) =
        naverHttpClient.geocode(geocodeRequest)
}
