package controller.routes

import controller.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.GithubSearchQuery

@Serializable
@Resource("/search")
data class Search(
    @SerialName("q") val query: String,
    val page: Int,
    @SerialName("per_page") val perPage: Int
) {
    companion object {
        suspend fun fetchRepositories(apiKey: String, query: GithubSearchQuery, page: Int, perPage: Int): String {
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
            return response.body()
        }
    }
}