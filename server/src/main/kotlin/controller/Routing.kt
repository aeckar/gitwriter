package controller

import io.ktor.server.application.*
import io.ktor.server.plugins.MissingRequestParameterException
import io.ktor.server.routing.*
import io.ktor.server.util.getOrFail
import model.Document

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

fun Application.configureRouting() {
    routing {
        get("/search/{repo...}") {

        }

        get("/site/{user}/{repo}/{path...}") {
            val user = call.parameters.getOrFail("user")
            val repo = call.parameters.getOrFail("repo")
            val path = call.parameters.getAll("path") ?: throw MissingRequestParameterException("path")
            val document = Document.get(user, repo)
            val page = document[path.joinToString("/")]

        }
    }
}