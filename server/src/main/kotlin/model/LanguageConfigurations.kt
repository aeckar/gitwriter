package model

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import controller.httpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString

// HTML -- docx4j
// PDF  -- Apache PDFBox

// option to pull-request on change
// login with github ^

// pdf and word are in embedded block (works for .doc too)
    // use google viewer API
        // <iframe src="https://docs.google.com/gview?url={pdf_doc_docx_url}&embedded=true"></iframe>

/** Maps the human-readable forms of each languages to their configuration. */
object LanguageConfigurations {
    val uneditableLanguages = mapOf(
        "Microsoft Word 2007+" to LanguageConfiguration(
            extensions = listOf(".docx"),
            textMateScope = "none",
            color = "#185ABD",
            isHumanReadable = false
        ),
        "PDF" to LanguageConfiguration(
            extensions = listOf(".pdf"),
            textMateScope = "none",
            color = "#B30B00",
            isHumanReadable = false
        )
    )

    lateinit var editableLanguages: Map<String, LanguageConfiguration>
    lateinit var supportedLanguages: Map<String, LanguageConfiguration>

    /**
     * If any HTTP request used to initialize this object results in an error,
     * that response is returned and initialization is aborted.
     */
    suspend fun initialize() = initializeEditableLanguages()

    private suspend fun initializeEditableLanguages(): HttpResponse {
        val response = httpClient.get {
            headers {
                append(HttpHeaders.ContentType, "application/yaml;charset=UTF-8")
                append(HttpHeaders.AcceptCharset, "ISO-8859-1")
                append(HttpHeaders.UserAgent, "Ktor Client")
            }
            url {
                protocol = URLProtocol.HTTPS
                host = "raw.githubusercontent.com"
                path("github-linguist/linguist/refs/heads/main/lib/linguist/languages.yml")
            }
        }
        if (!response.status.isSuccess()) {
            return response
        }
        val yaml = Yaml(configuration = YamlConfiguration(
            strictMode = false,
            decodeEnumCaseInsensitive = true
        ))
        editableLanguages = yaml.decodeFromString<Map<String, LanguageConfiguration>>(response.bodyAsText())
            .filter { (name) -> name in setOf("HTML", "Markdown", "AsciiDoc") }
        supportedLanguages = editableLanguages + uneditableLanguages
        return response
    }
}
