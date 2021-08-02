package com.hojongs.navermapskt.reversegc

import kotlinx.serialization.Serializable

@Serializable
data class ReverseGCRequest(
    val coordsX: String,
    val coordsY: String,
    val request: Request = Request.COORDS_TO_ADDR,
    val sourcecrs: Crs = Crs.EPSG4326,
    val targetcrs: Crs = Crs.EPSG4326,
    val orders: List<Order> = listOf(Order.LEGALCODE, Order.ADMCODE),
    val output: Output = Output.XML,
    val callback: String? = null,
) {
    fun ordersParamValue(): String = orders.joinToString(",") { it.paramValue }

    enum class Request(val paramValue: String) {
        COORDS_TO_ADDR("coordsToaddr")
    }

    enum class Crs(val paramValue: String) {
        EPSG4326("epsg:4326"),
        EPSG3857("epsg:3857"),
        NHN2048("nhn:2048"),
        NHN128("nhn:128"),
    }

    enum class Order(val paramValue: String) {
        LEGALCODE("legalcode"),
        ADMCODE("admcode"),
        ADDR("addr"),
        ROADADDR("roadaddr"),
    }

    enum class Output(val paramValue: String) {
        JSON("json"),
        XML("xml"),
    }
}
