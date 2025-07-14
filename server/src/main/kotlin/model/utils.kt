package model

import controller.httpClient
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.headers
import io.ktor.server.plugins.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import model.vcs.Repository

private const val RAW_CONTENT_HOST = "https://raw.githubusercontent.com"
const val GITHUB_API = "https://api.github.com"
const val userAgent = "Ktor client"
lateinit var githubApiKey: String

/**
 * Given a path to a file, returns the directory it resides in.
 *
 * If the file resides in the root directory, an empty string is returned.
 * @see fileName
 */
fun String.parentDirectory(): String {
    val segments = split('/')
    return segments.asSequence()
        .take(segments.size - 1)
        .joinToString("/")
}


/**
 * Returns the file or folder name given by the path.
 * @see parentDirectory
 */
fun String.fileName() = substringAfterLast('/')

/** Deserializes the JSON object contained by the string. */
fun String.decodeJson() = Json.decodeFromString(JsonObject.serializer(), this)

/**
 * Returns the contents of the file at the given location, relative to the root directory
 * of the GitHub repository of the same user and name.
 */
suspend fun content(repo: Repository, path: String): ByteReadChannel? {
    val contentUrl = "$RAW_CONTENT_HOST/$repo/HEAD/$path"
    val response = httpClient.get(contentUrl) {
        headers { append(HttpHeaders.UserAgent, userAgent) }
    }
    return if (response.status.isSuccess()) response.bodyAsChannel() else null
}

/**
 * Performs a GET request on the specified endpoint of the [GitHub API][GITHUB_API]
 * with proper authorization to decrease the likelihood of rate limiting.
 *
 * The request expects a response of a JSON object.
 */
suspend fun github(endpoint: String): HttpResponse {
    return httpClient.get("$GITHUB_API/$endpoint") {
        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.Authorization, "Bearer $githubApiKey")
            append(HttpHeaders.UserAgent, userAgent)
        }
    }
}

/** Returns the object if not null. Otherwise, throws an [NotFoundException] with the given message. */
inline fun <T : Any> T?.orResourceNotFound(message: () -> String): T {
    if (this != null) {
        return this
    }
    throw NotFoundException("${message()} not found")
}

/**
 * Returns the response object if the request was a success.
 * Otherwise, throws an [NotFoundException] with the given message.
 */
inline fun HttpResponse.orResourceNotFound(message: () -> String): HttpResponse {
    if (status.isSuccess()) {
        return this
    }
    throw NotFoundException("${message()} not found")
}