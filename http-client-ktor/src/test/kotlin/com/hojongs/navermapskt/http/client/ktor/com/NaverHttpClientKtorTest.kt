package com.hojongs.navermapskt.http.client.ktor.com

import com.hojongs.navermapskt.NaverClientConfig
import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import io.kotest.assertions.throwables.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.ktor.client.features.*
import io.ktor.http.*
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

    should("throw exception with Unauthorized when client secret is invalid") {
        val config = NaverClientConfig(
            System.getenv("NAVER_MAPS_CLIENT_ID"),
            "asdfasdf"
        )
        val client = NaverHttpClientKtor(config)

        val exception = shouldThrow<ClientRequestException> {
            client.geocode(GeocodeRequest(""))
        }
        exception.response.status shouldBe HttpStatusCode.Unauthorized
    }

    should("throw exception with Unauthorized when client id and client secret are empty") {
        val config = NaverClientConfig("", "")
        val client = NaverHttpClientKtor(config)

        val exception = shouldThrow<ClientRequestException> {
            client.geocode(GeocodeRequest(""))
        }
        exception.response.status shouldBe HttpStatusCode.Unauthorized
    }

    should("error when query is empty string") {
        val config = NaverClientConfig(
            System.getenv("NAVER_MAPS_CLIENT_ID"),
            System.getenv("NAVER_MAPS_CLIENT_SECRET"),
        )
        val client = NaverHttpClientKtor(config)

        val geocode = client.geocode(GeocodeRequest(""))

        print(geocode)

        geocode.status shouldBe "INVALID_REQUEST"
        geocode.errorMessage shouldBe "query is INVALID"
    }

    should("success decoding json") {
        // given
        val str =
            javaClass
                .getResource("/geocode.json")!!
                .readText()

        shouldNotThrow<Throwable> {
            Json.decodeFromString<Geocode>(str)
        }
    }
})
