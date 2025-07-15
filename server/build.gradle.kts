plugins {
    val kotlinVersion = "2.2.0"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("io.ktor.plugin") version "3.2.1"
}

group = "io.github.aeckar"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {  // todo configure CORS
    val logbackVersion = "1.4.14"
    val ktomlVersion = "0.7.0"

    implementation(platform("io.ktor:ktor-bom:_"))
    testImplementation(platform("io.ktor:ktor-bom:_"))

    // Ktor server
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-host-common")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-cors")

    // Ktor client
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-client-logging")
    implementation("io.ktor:ktor-client-cio")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion") // or from version catalog

    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("com.akuleshov7:ktoml-core:$ktomlVersion")
    implementation("com.akuleshov7:ktoml-file:$ktomlVersion")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation("org.commonmark:commonmark:0.21.0")
}
