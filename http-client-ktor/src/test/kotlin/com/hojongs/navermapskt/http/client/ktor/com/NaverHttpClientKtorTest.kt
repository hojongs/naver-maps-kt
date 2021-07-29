package com.hojongs.navermapskt.http.client.ktor.com

import com.hojongs.navermapskt.Geocode
import com.hojongs.navermapskt.GeocodeRequest
import com.hojongs.navermapskt.NaverClientConfig
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class NaverHttpClientKtorTest : ShouldSpec({
    should("return correct geocode with status OK") {
        val config = NaverClientConfig(
            System.getenv("NAVER_MAPS_CLIENT_ID"),
            System.getenv("NAVER_MAPS_CLIENT_SECRET"),
        )
        val client = NaverHttpClientKtor(config)

        val geocode = client.geocode(GeocodeRequest("분당구 불정로 6"))

        geocode.status shouldBe "OK"
        geocode.addresses?.get(0)?.roadAddress shouldBe "경기도 성남시 분당구 불정로 6 NAVER그린팩토리"
    }

    should("success decoding json") {
        val str = """
        {"status":"OK","meta":{"totalCount":1,"page":1,"count":1},"addresses":[{"roadAddress":"경기도 성남시 분당구 불정로 6 NAVER그린팩토리","jibunAddress":"경기도 성남시 분당구 정자동 178-1 NAVER그린팩토리","englishAddress":"6, Buljeong-ro, Bundang-gu, Seongnam-si, Gyeonggi-do, Republic of Korea","addressElements":[{"types":["SIDO"],"longName":"경기도","shortName":"경기도","code":""},{"types":["SIGUGUN"],"longName":"성남시 분당구","shortName":"성남시 분당구","code":""},{"types":["DONGMYUN"],"longName":"정자동","shortName":"정자동","code":""},{"types":["RI"],"longName":"","shortName":"","code":""},{"types":["ROAD_NAME"],"longName":"불정로","shortName":"불정로","code":""},{"types":["BUILDING_NUMBER"],"longName":"6","shortName":"6","code":""},{"types":["BUILDING_NAME"],"longName":"NAVER그린팩토리","shortName":"NAVER그린팩토리","code":""},{"types":["LAND_NUMBER"],"longName":"178-1","shortName":"178-1","code":""},{"types":["POSTAL_CODE"],"longName":"13561","shortName":"13561","code":""}],"x":"127.1054065","y":"37.3595669","distance":0.0}],"errorMessage":""}
        """.trimIndent()

        shouldNotThrow<Throwable> {
            val geocode = Json.decodeFromString<Geocode>(str)
            print(geocode)
        }
    }
})
