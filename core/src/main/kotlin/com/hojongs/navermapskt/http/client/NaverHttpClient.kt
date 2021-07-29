package com.hojongs.navermapskt.http.client

import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest

/**
 * HTTP Client for Naver Maps REST API
 *
 * @see com.hojongs.navermapskt.NaverClientConfig
 */
interface NaverHttpClient {

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
    suspend fun geocode(geocodeRequest: GeocodeRequest): Geocode
}
