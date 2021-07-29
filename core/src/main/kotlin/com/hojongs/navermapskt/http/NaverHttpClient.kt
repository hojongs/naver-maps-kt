package com.hojongs.navermapskt.http

import com.hojongs.navermapskt.Geocode
import com.hojongs.navermapskt.GeocodeRequest
import com.hojongs.navermapskt.NaverClientConfig

abstract class NaverHttpClient {
    protected abstract val naverClientConfig: NaverClientConfig

    /**
     * Method: GET
     * URL: https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode
     * Query Params
     * - query: string (Required)
     * - coordinate: string
     * - filter: string
     * - page: number
     * - count: number
     *
     * For more information, please refer https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
     */
    abstract suspend fun geocode(geocodeRequest: GeocodeRequest): Geocode
}
