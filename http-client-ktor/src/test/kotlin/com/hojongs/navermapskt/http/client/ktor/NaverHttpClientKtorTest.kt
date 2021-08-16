package com.hojongs.navermapskt.http.client.ktor

import com.hojongs.navermapskt.NaverClientConfig
import com.hojongs.navermapskt.geocode.Geocode
import com.hojongs.navermapskt.geocode.GeocodeRequest
import com.hojongs.navermapskt.reversegc.ReverseGCRequest
import com.hojongs.navermapskt.staticmap.StaticMapRequest
import io.kotest.assertions.throwables.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.ktor.client.features.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class NaverHttpClientKtorTest : DescribeSpec({
    fun getEnv(envName: String) =
        System.getenv(envName)
            ?: throw Exception("Env variable is required : $envName")

    val config = NaverClientConfig(
        getEnv("NAVER_MAPS_CLIENT_ID"),
        getEnv("NAVER_MAPS_CLIENT_SECRET"),
    )
    val client = NaverHttpClientKtor(config)

    afterSpec {
        client.close()
    }

    describe("geocode") {
        it("return correct geocode with status OK") {
            val geocode = client.geocode(GeocodeRequest("분당구 불정로 6"))

            geocode.status shouldBe "OK"
            geocode.addresses?.get(0)?.roadAddress shouldBe "경기도 성남시 분당구 불정로 6 NAVER그린팩토리"
        }

        it("throw exception with Unauthorized when client secret is invalid") {
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

        it("throw exception with Unauthorized when client id and client secret are empty") {
            val config = NaverClientConfig("", "")
            val client = NaverHttpClientKtor(config)

            val exception = shouldThrow<ClientRequestException> {
                client.geocode(GeocodeRequest(""))
            }
            exception.response.status shouldBe HttpStatusCode.Unauthorized
        }

        it("error when query is empty string") {
            val geocode = client.geocode(GeocodeRequest(""))

            print(geocode)

            geocode.status shouldBe "INVALID_REQUEST"
            geocode.errorMessage shouldBe "query is INVALID"
        }
    }

    describe("reverseGeocode") {
        it("return correct response") {
            val reverseGcResponse = client.reverseGeocode(
                ReverseGCRequest(
                    "129.1133567",
                    "35.2982640",
                    output = ReverseGCRequest.Output.JSON,
                )
            )

            reverseGcResponse.status.name shouldBe "ok"
            reverseGcResponse.results[0].let {
                it.name shouldBe "legalcode"
                it.region.area1.name shouldBe "부산광역시"
            }
            reverseGcResponse.results[1].name shouldBe "admcode"
        }
    }

    describe("staticMap") {
        it("return correct png") {
            val bytes = client.staticMap(
                StaticMapRequest(
                    w = 300,
                    h = 300,
                    centerOrMarkers = StaticMapRequest.CenterOrMarkers.Center(
                        center = "127.1054221,37.3591614",
                        level = 16,
                    ),
                )
            )

            val expected = javaClass
                .getResource("/staticmap.png")!!
                .readBytes()
            bytes shouldBe expected
        }
    }

    describe("json") {
        it("success decoding json") {
            // given
            val str =
                javaClass
                    .getResource("/geocode.json")!!
                    .readText()

            shouldNotThrow<Throwable> {
                Json.decodeFromString<Geocode>(str)
            }
        }
    }

    describe("logLevel") {
        listOf(
            "ALL",
            "HEADERS",
            "BODY",
            "INFO",
            "NONE",
        ).forEach { logLevel ->
            it("$logLevel: should work as logLevel") {
                val config = NaverClientConfig("", "")

                shouldNotThrow<IllegalArgumentException> {
                    NaverHttpClientKtor(config, logLevel)
                }
            }
        }
    }
})
