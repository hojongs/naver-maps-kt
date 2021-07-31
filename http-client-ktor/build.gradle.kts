val ktorVersion = "1.6.1"
val logbackVersion = "1.2.5"

dependencies {
    implementation("com.hojongs:naver-maps-kt-core:$version")

    implementation("io.ktor:ktor-client-core:$ktorVersion")

    // serialize or deserialize json or others
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    // ktor client engine
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    // ktor client logging
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}
