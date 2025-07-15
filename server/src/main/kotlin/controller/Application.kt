package controller

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.runBlocking
import model.content.Document
import model.content.Page
import model.githubApiKey
import model.vcs.Repository
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.installPlugins() {
    install(CallLogging) {
        logger = LoggerFactory.getLogger("Application")
        level = Level.INFO
    }
    install(ContentNegotiation) {
        json(jsonConfiguration)
    }
    install(CORS) {
        allowMethod(HttpMethod.Get)
        //allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        anyHost() // fixme: for dev only! use specific domains in production
    }
}

fun Application.configureRouting() {
    routing {
        get("/pages/{user}/{repo}/{path...}") {
            val user = call.parameters.getOrFail("user")
            val repoName = call.parameters.getOrFail("repo")
            val path = call.parameters.getAll("path")?.joinToString("/") ?: throw MissingRequestParameterException("path")
            val repo = Repository(user, repoName)
            val document = Document.get(repo)
            val content = document[path]
            call.respond(Page(document, content))
        }
    }
}

fun Application.module() {
    runBlocking {
        installPlugins()
        configureRouting()
        githubApiKey = environment.config.property("ktor.apiKeys.githubApiKey").getString()
    }
}