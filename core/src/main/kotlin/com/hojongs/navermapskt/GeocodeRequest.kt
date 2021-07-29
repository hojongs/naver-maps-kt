package com.hojongs.navermapskt

data class GeocodeRequest(
    val query: String,
    val coordinate: String? = null,
    val filter: String? = null,
    val page: Long? = null,
    val count: Long? = null,
)
