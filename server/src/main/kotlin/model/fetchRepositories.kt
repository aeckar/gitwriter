package model

import controller.httpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/** Performs a repository search using the GitHub API. */
suspend fun fetchRepositories(apiKey: String, query: RepositoryQuery, page: Int, perPage: Int): String {
    val response = httpClient.get {
        headers {
            appendCommonHeaders()
            append(HttpHeaders.Authorization, "Bearer $apiKey")
        }
        url {
            protocol = URLProtocol.HTTPS
            host = "api.github.com"
            path("search/repositories")
            parameter("q", "$query&page=$page&per_page=$perPage&order=desc")
        }
    }
    // todo handle failure
    return response.bodyAsText()
}