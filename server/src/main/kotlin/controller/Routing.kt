package controller

import controller.routes.Languages
import controller.routes.Licenses
import controller.routes.Search
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.GithubSearchQuery
import model.fetchRepositories

// todo implement caching

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    install(Resources)
    routing {
        post("/render") {
            // todo convert to rich text
            // todo receive rich text, display using library
        }
        get("/filters") {

        }
        get<Search> { (query, page, perPage) ->
            val apiKey = environment.config.property("GITHUB_SEARCH_API_KEY").getString()
            val repositories = Search.fetchRepositories(apiKey, GithubSearchQuery.parse(query), page, perPage)
            call.respondText(repositories, ContentType.Application.Json)
        }
        get<Languages> { (key) ->

        }
        get<Licenses> { (key) ->

        }
    }
}