package com.hojongs.navermapskt.http.client

import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.reversegc.ReverseGCRequest
import com.hojongs.navermapskt.reversegc.ReverseGCResponse
import com.hojongs.navermapskt.staticmap.StaticMapRequest

/**
 * HTTP Client for Naver Maps REST API
 *
 * @see com.hojongs.navermapskt.NaverClientConfig
 */
interface NaverHttpClient {

    /**
     * Method: GET
     *
     * URL: https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode
     *
     * Query Params
     * - query: string (Required)
     * - coordinate: string
     * - filter: string
     * - page: number
     * - count: number
     *
     * For more information, please refer https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding
     */
    suspend fun geocode(geocodeRequest: GeocodeRequest): Geocode

    /**
     * Method: GET
     *
     * URL: https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc
     *
     * Query Params
     * - ...
     *
     * For more information, please refer https://api.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding
     */
    suspend fun reverseGeocode(reverseGcRequest: ReverseGCRequest): ReverseGCResponse

    suspend fun staticMap(staticMapRequest: StaticMapRequest): ByteArray

    /**
     * Free up the resources
     *
     * You need to call it after you finish working with the HTTP client
     */
    fun close()
}
