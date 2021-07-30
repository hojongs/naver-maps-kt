package com.hojongs.navermapskt.reversegc

import kotlinx.serialization.Serializable

@Serializable
data class ReverseGCResponse(
    val results: List<Result>,
    val status: Status
) {
    @Serializable
    data class Result(
        val code: Code,
        val name: String,
        val region: Region
    ) {
        @Serializable
        data class Code(
            val id: String,
            val mappingId: String,
            val type: String
        )

        @Serializable
        data class Region(
            val area0: Area,
            val area1: Area,
            val area2: Area,
            val area3: Area,
            val area4: Area,
        ) {
            @Serializable
            data class Area(
                val coords: Coords,
                val name: String,
                val alias: String? = null,
            ) {
                @Serializable
                data class Coords(val center: Center) {
                    @Serializable
                    data class Center(
                        val crs: String,
                        val x: Double,
                        val y: Double
                    )
                }
            }
        }
    }

    @Serializable
    data class Status(
        val code: Int,
        val message: String,
        val name: String
    )
}
