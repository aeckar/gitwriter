plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "io.github.aeckar"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

[versions]
kotlin-version = "2.1.10"
ktor-version = "3.1.3"
logback-version = "1.5.18"
ktoml-version = "0.7.0"

[libraries]
ktor-server-content-negotiation = { module = "io.ktor:ktor-server-content-negotiation", version.ref = "ktor-version" }
ktor-server-core = { module = "io.ktor:ktor-server-core", version.ref = "ktor-version" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor-version" }
ktor-server-host-common = { module = "io.ktor:ktor-server-host-common", version.ref = "ktor-version" }
ktor-server-status-pages = { module = "io.ktor:ktor-server-status-pages", version.ref = "ktor-version" }
ktor-server-resources = { module = "io.ktor:ktor-server-resources", version.ref = "ktor-version" }
ktor-server-netty = { module = "io.ktor:ktor-server-netty", version.ref = "ktor-version" }
logback-classic = { module = "ch.qos.logback:logback-classic", version.ref = "logback-version" }
ktor-server-config-yaml = { module = "io.ktor:ktor-server-config-yaml", version.ref = "ktor-version" }
ktor-server-test-host = { module = "io.ktor:ktor-server-test-host", version.ref = "ktor-version" }
kotlin-test-junit = { module = "org.jetbrains.kotlin:kotlin-test-junit", version.ref = "kotlin-version" }
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor-version" }
ktor-client-cio = { module = "io.ktor:ktor-client-cio", version.ref = "ktor-version" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor-version" }
ktoml-core = { module = "com.akuleshov7:ktoml-core", version.ref = "ktoml.version" }
ktoml-file = { module = "com.akuleshov7:ktoml-file", version.ref = "0.7.0")

    [plugins]
    kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin-version" }
    ktor = { id = "io.ktor.plugin", version.ref = "ktor-version" }
    kotlin-plugin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin-version" }

dependencies {
    implementation(platform("io.ktor:ktor-bom:_"))
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation()
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
