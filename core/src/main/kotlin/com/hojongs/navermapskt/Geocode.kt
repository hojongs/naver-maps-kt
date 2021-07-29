package com.hojongs.navermapskt

import kotlinx.serialization.Serializable

@Serializable
data class Geocode(
    val status: String,
    val errorMessage: String? = null,
    val meta: Meta? = null,
    val addresses: List<Address>? = null,
) {
    @Serializable
    data class Meta(
        val totalCount: Long? = null,
        val page: Long? = null,
        val count: Long? = null,
    )

    @Serializable
    data class Address(
        val roadAddress: String? = null,
        val jibunAddress: String? = null,
        val englishAddress: String? = null,
        val x: String? = null,
        val y: String? = null,
        val distance: Double? = null,
        val addressElements: List<Element>? = null,
    ) {
        @Serializable
        data class Element(
            val types: List<String>? = null,
            val longName: String? = null,
            val shortName: String? = null,
            val code: String? = null,
        )
    }
}
