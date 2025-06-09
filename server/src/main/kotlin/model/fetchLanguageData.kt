package model

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import controller.httpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.serialization.decodeFromString

/**
 * Fetches additional language information for HTML, Markdown, and AsciiDoc
 * from the `github-linguist/linguist` repository.
 * @see markupLanguages
 */
suspend fun Application.fetchLanguageData(): Map<String, MarkupLanguage> {
    val response = httpClient.get {
        headers {
            append(HttpHeaders.ContentType, "application/yaml;charset=UTF-8")
            append(HttpHeaders.AcceptCharset, "ISO-8859-1")
            append(HttpHeaders.UserAgent, "Ktor Client")
        }
        url {
            protocol = URLProtocol.HTTPS
            host = contentUrl
            path("github-linguist/linguist/$defaultBranch/lib/linguist/languages.yml")
        }
    }
    if (!response.status.isSuccess()) {
        log.info("Failed to fetch language data: ${response.status.description}")
        log.info("Falling back to default language data")
        return markupLanguages
    }
    val yaml = Yaml(configuration = YamlConfiguration(
        strictMode = false,
        decodeEnumCaseInsensitive = true
    ))
    return yaml
        .decodeFromString<Map<String, MarkupLanguage>>(response.bodyAsText())
        .filter { (name) -> name in setOf("HTML", "Markdown", "AsciiDoc") }
}