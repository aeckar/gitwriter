package model

import controller.httpClient
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.headers
import io.ktor.server.plugins.NotFoundException
import io.ktor.utils.io.ByteReadChannel

private const val CONTENT_HOST = "https://raw.githubusercontent.com"

/**
 * Returns the contents of the file at the given location, relative to the root directory
 * of the GitHub repository of the same user and name.
 */
suspend fun content(repo: Repository, path: String): ByteReadChannel? {
    val contentUrl = "$CONTENT_HOST/$repo/HEAD/$path"
    val response = httpClient.get(contentUrl) {
        headers {
            append(HttpHeaders.UserAgent, "ktor client")
        }
    }
    return if (response.status.isSuccess()) response.bodyAsChannel() else null
}

/** Returns the object if not null. Otherwise, throws an [NotFoundException] with the given message. */
inline fun <T : Any> T?.orResourceNotFound(message: () -> String): T {
    if (this != null) {
        return this
    }
    throw NotFoundException(message())
}