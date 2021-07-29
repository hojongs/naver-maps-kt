package com.hojongs.navermapskt

/**
 * @property clientId a value of Http Header X-NCP-APIGW-API-KEY-ID
 * @property clientSecret a value of Http X-NCP-APIGW-API-KEY
 */
data class NaverClientConfig(
    val clientId: String,
    val clientSecret: String,
)
