package model

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import controller.httpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString

// HTML -- docx4j
// PDF  -- Apache PDFBox

// option to pull-request on change
// login with github ^

// Space - Host your static content
// pdf and word are in embedded block (works for .doc too)
    // use google viewer API
        // <iframe src="https://docs.google.com/gview?url={pdf_doc_docx_url}&embedded=true"></iframe>

// no show logo an

// Use cases:
//  - study notes
//  - courses
//  - documentation/technical writing
//  - blogs

// left-side sections should be given by user
// right-side come from document

/**
 * Each key is the human-readable name of each language.
 */
private val supportedLanguages: Map<String, LanguageConfiguration> = runBlocking {
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
    val yaml = Yaml(configuration = YamlConfiguration(
        strictMode = false,
        decodeEnumCaseInsensitive = true
    ))
    val supportedLanguages = setOf("HTML", "Markdown", "AsciiDoc")
    val editableLanguages = yaml.decodeFromString<Map<String, LanguageConfiguration>>(response.bodyAsText())
        .filter { (name) -> name in supportedLanguages }
    editableLanguages + uneditableLanguages
}