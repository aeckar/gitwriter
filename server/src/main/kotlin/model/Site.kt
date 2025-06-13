package model

import controller.httpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path

/** Describes a rendered website, whose markup is stored in a GitHub repository. */
class Site(val repoUrl: String, val config: SiteConfiguration) {
    /** Returns `null` if the resource cannot be found. */
    suspend fun fetchResource(path: String): String? {
        val response = httpClient.get {
            headers {
                append(HttpHeaders.Accept, "text/json;charset=UTF-8")
                append(HttpHeaders.AcceptCharset, "ISO-8859-1")
                append(HttpHeaders.UserAgent, "Ktor Client")
            }
            url {
                protocol = URLProtocol.HTTPS
                host = contentUrl
                path("$repoUrl/$defaultBranch/$path")
            }
        }
        if (!response.status.isSuccess()) {
            return null
        }
        return response.bodyAsText()
    }
}