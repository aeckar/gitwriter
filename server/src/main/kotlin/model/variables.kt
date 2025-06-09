package model

/**
 * The markup languages than can be rendered as a webpage.
 *
 * Currently, HTML, Markdown, and AsciiDoc are supported.
 * @see fetchLanguageData
 */
var markupLanguages = mapOf(
    "HTML" to MarkupLanguage(listOf(".html", ".htm")),
    "Markdown" to MarkupLanguage(listOf(".md")),
    "AsciiDoc" to MarkupLanguage(listOf(".adoc"))
)

/** When appended to a GitHub repository URL, resolves to the default branch. */
const val defaultBranch = "refs/heads/HEAD"

/** The URL containing raw GitHub user content. */
const val contentUrl = "raw.githubusercontent.com"