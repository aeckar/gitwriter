package controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import model.content.Document
import model.content.Page
import model.vcs.Repository

/*
navigator.sendBeacon('/api/cleanup', JSON.stringify({ sessionId }))
sendBeacon() is the only reliable way to send a request during beforeunload (e.g., tab close).

It only supports POST, with no custom method override.

That rules out DELETE for session cleanup on tab/browser exit.

onBeforeRouteLeave((to, from) => {
fetch('/api/cleanup', {
method: 'POST',
body: JSON.stringify({ sessionId: yourSessionId }),
headers: { 'Content-Type': 'application/json' }
});
});

I receive:
session_id cookie


param = uname/repo/path

I send:
// */
// todo later: search api

fun Application.configureRouting() {
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        anyHost() // fixme: for dev only! use specific domains in production
    }

    routing {
        get("/pages/{user}/{repo}/{path...}") {
            val user = call.parameters.getOrFail("user")
            val repoName = call.parameters.getOrFail("repo")
            val path = call.parameters.getAll("path")?.joinToString("/") ?: throw MissingRequestParameterException("path")
            val repo = Repository(user, repoName)
            val document = Document.get(repo)
            val contentHtml = document[path]
            call.respond(Page(document, contentHtml))
        }
    }
}