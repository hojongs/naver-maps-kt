package com.hojongs.navermapskt

import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.http.client.NaverHttpClient
import com.hojongs.navermapskt.reversegc.ReverseGCRequest
import com.hojongs.navermapskt.staticmap.StaticMapRequest
import io.kotest.core.spec.style.*
import org.mockito.kotlin.*

internal class NaverMapsServiceTest : ShouldSpec({
    val mockClient = mock<NaverHttpClient> {
        onBlocking { geocode(any()) } doReturn Geocode("OK")
    }
    val naverMapsService = NaverMapsService(mockClient)

    should("proxy naverHttpClient.geocode") {
        val req = GeocodeRequest("")
        naverMapsService.geocode(req)

        verify(mockClient).geocode(req)
    }
    should("proxy naverHttpClient.reverseGeocode") {
        val req = ReverseGCRequest("1.1", "1.1")
        naverMapsService.reverseGeocode(req)

        verify(mockClient).reverseGeocode(req)
    }
    should("proxy naverHttpClient.staticMap") {
        val req = StaticMapRequest(
            w = 10, h = 10,
            centerOrMarkers = StaticMapRequest.CenterOrMarkers.Center(
                center = "127.1054221,37.3591614",
                level = 16,
            )
        )
        naverMapsService.staticMap(req)

        verify(mockClient).staticMap(req)
    }
})
