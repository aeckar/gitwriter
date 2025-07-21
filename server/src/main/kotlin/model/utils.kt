package model

import controller.client
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.headers
import io.ktor.server.plugins.*
import io.ktor.utils.io.*
import model.vcs.Repository

private const val RAW_CONTENT_HOST = "https://raw.githubusercontent.com"
const val GITHUB_API = "https://api.github.com"
const val USER_AGENT = "Ktor client"
lateinit var githubApiKey: String

/**
 * Removes the leading `"/"`, if present, so the path can be used as a subdirectory
 * while avoiding strings like `"//"` after concatenation.
 */
fun path(vararg segments: String): String {
    if (segments.isEmpty()) {
        return ""
    }
    if (segments.size == 1) {
        return segments.single()
    }
    val subDirs = segments.asSequence()
        .drop(1)
        .joinToString("/") { it.removePrefix("/").removeSuffix("/") }
    val rootDir = segments.first().removeSuffix("/")
    return "$rootDir/$subDirs"
}

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

/**
 * Returns the contents of the file at the given location, relative to the root directory
 * of the GitHub repository of the same user and name.
 */
suspend fun content(repo: Repository, path: String): ByteReadChannel? {
    val contentUrl = path("$RAW_CONTENT_HOST/$repo/HEAD", path)
    val response = client.get(contentUrl) {
        headers {
            append(HttpHeaders.Authorization, "Bearer $githubApiKey")
            append(HttpHeaders.UserAgent, USER_AGENT)
        }
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
    return client.get(path(GITHUB_API, endpoint)) {
        headers {
            append(HttpHeaders.Accept, "application/json")
            append(HttpHeaders.Authorization, "Bearer $githubApiKey")
            append(HttpHeaders.UserAgent, USER_AGENT)
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