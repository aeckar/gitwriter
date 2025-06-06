package controller

import controller.routes.Languages
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking

val httpClient = HttpClient {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
    expectSuccess = true
}

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() = runBlocking {
    configureSerialization()
    configureRouting()
    Languages.fetch()
}