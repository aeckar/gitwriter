package model

import controller.httpClient
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.headers
import kotlinx.io.IOException

private const val CONTENT_HOST = "https://raw.githubusercontent.com"

/**
 * Returns the contents of the file at the given location, relative to the root directory
 * of the GitHub repository of the same user and name.
 */
suspend fun content(repo: Repository, path: String): String {
    val contentUrl = "$CONTENT_HOST/$repo/HEAD/$path"
    val response = httpClient.get(contentUrl) {
        headers {
            append(HttpHeaders.UserAgent, "ktor client")
        }
    }
    if (!response.status.isSuccess()) {
        throw IOException("Content '/$path' in repository '$repo' not found")
    }
    return response.bodyAsText()
}