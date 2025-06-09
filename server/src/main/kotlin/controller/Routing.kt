package controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.MissingRequestParameterException
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.getOrFail
import model.RepositoryQuery
import model.fetchRepositories

// todo cache repo data by http status

// todo hardcode language/license autocomplete in client
// todo serve language data to client ONCE

private operator fun Parameters.get(vararg names: String): List<String> = names.map { getOrFail(it) }
private fun Parameters.getAllOrFail(name: String) = getAll(name) ?: throw MissingRequestParameterException(name)

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    install(Resources)
    routing {
        post("/editor") {

        }
        patch("/editor") {

            // todo convert to rich text
            // todo if enter after ([{, generate other and indent
            // todo on undo/redo, serve cached text
            // todo on invalidation of redo's, abandon that part of cache
            // todo receive rich text, display as html contenteditable

            /*
            I receive:

            text changes
            editor_id cookie
            --- OR ---
            notice of termination of editing session

            I serve:
            rich text html
             */
        }

        delete("/editor") {

        }

        get("/site/{path...}") {
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
             */

            val repoUrl = call.parameters.getAllOrFail("path")

        }

        get("/search") {
            val apiKey = environment.config.property("GITHUB_SEARCH_API_KEY").getString()
            val (query, page, perPage) = call.queryParameters["q", "page", "per_page"]
            val repositories = fetchRepositories(apiKey, RepositoryQuery.parse(query), page.toInt(), perPage.toInt())
            call.respondText(repositories, ContentType.Application.Json)
        }
    }
}