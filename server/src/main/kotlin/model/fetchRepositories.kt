package model

import controller.httpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders

/** Performs a repository search using the GitHub API. */
suspend fun fetchRepositories(apiKey: String, query: RepositoryQuery, page: Int, perPage: Int): String {
    val response = httpClient.get {
        headers {
            append(HttpHeaders.Accept, "text/json;charset=UTF-8")
            append(HttpHeaders.AcceptCharset, "ISO-8859-1")
            append(HttpHeaders.UserAgent, "Ktor Client")
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