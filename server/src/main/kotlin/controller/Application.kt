package controller

import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import model.fetchLanguageData
import model.markupLanguages

// ':' then autocomplete filter, then autocomplete value
// forks: followers: stars: created: pushed: topic: size: owner:
// language:<AUTOCOMPLETE>
// license:<AUTOCOMPLETE>
// archived:true|false

// use existing textmate grammars + monaco to render text

/*
    - dt: assignable attributes (css classes) to elements -- needs attribute literal & simple formatting dsl (support for CSS too)
    - navigation sidebar (like kotlin documentation)
        - pages on left
        - text sections on right (marker) for
        - solid menu bar on top with logo, then search (bar), then upload (icon), then advanced repo search (icon)
    - author
    - [raw | render] - toggle
    -
 */

// support user upload-conversion

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
    markupLanguages = fetchLanguageData()
}