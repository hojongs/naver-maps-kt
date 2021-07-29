package com.hojongs.navermapskt

import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.client.NaverHttpClient
import com.hojongs.navermapskt.reversegc.ReverseGCRequest
import com.hojongs.navermapskt.reversegc.ReverseGCResponse

class NaverMapsService(
    private val naverHttpClient: NaverHttpClient,
) {
    suspend fun geocode(geocodeRequest: GeocodeRequest): Geocode =
        naverHttpClient.geocode(geocodeRequest)

    suspend fun reverseGeocode(reverseGCRequest: ReverseGCRequest): ReverseGCResponse =
        naverHttpClient.reverseGeocode(reverseGCRequest)
}
