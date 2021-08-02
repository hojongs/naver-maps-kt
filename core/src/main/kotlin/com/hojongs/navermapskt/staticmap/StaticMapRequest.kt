package com.hojongs.navermapskt.staticmap

/**
 * @param w 1~1024
 * @param h 1~1024
 */
data class StaticMapRequest(
    val crs: Crs = Crs.EPSG4326,
    val centerOrMarkers: CenterOrMarkers,
    val w: Int,
    val h: Int,
    val mapType: MapType = MapType.BASIC,
    val format: Format = Format.JPG,
    val scale: Scale = Scale.SCALE1,
    val lang: Lang = Lang.KO,
    val publicTransit: Boolean = false,
    val dataversion: String? = null,
) {
    enum class Crs(val paramValue: String) {
        EPSG4326("EPSG:4326"),
        EPSG4258("EPSG:4258"),
        EPSG4162("EPSG:4162"),
        EPSG2096("EPSG:2096"),
        EPSG2097("EPSG:2097"),
        EPSG2098("EPSG:2098"),
        EPSG385("EPSG:385"),
        EPSG5179("EPSG:5179"),
        EPSG3857("epsg:3857"),
        NHN2048("nhn:2048"),
        NHN128("nhn:128"),
    }

    sealed class CenterOrMarkers {
        /**
         * @param level 0~20
         */
        class Center(val center: String, val level: Int) : CenterOrMarkers()
        class Markers(val markers: String) : CenterOrMarkers()
    }

    enum class MapType(val paramValue: String) {
        BASIC("basic"),
        TRAFFIC("traffic"),
        SATELLITE("satellite"),
        SATELLITE_BASE("satellite_base"),
        TERRAIN("terrain"),
    }

    enum class Format(val paramValue: String) {
        JPG("jpg"),
        PNG8("png8"),

        /**
         * 24 bits
         */
        PNG("png"),
    }

    enum class Scale(val paramValue: Int) {
        SCALE1(1),
        SCALE2(2),
    }

    enum class Lang(val paramValue: String) {
        KO("ko"),
        EN("en"),
        JA("ja"),
        ZH("zh"),
    }
}
