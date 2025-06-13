package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// todo in markdown, show sanitized tags
// https://github.com/OWASP/java-html-sanitizer?tab=readme-ov-file -- use all default sanitizers

/**
 * [Site] configuration, as written in `site.yml`.
 *
 * Pattern matching for files and directories is defined by [java.nio.file.FileSystem.getPathMatcher].
 * @param name the name of the website, which will be displayed in the navigation bar of the completed website
 * @param homePage a path pattern matching the first document to be rendered
 * and displayed first upon visiting the website
 * @param exposesCode if true, documents can be toggled between their code and their render
 * If a repository is archived, the user will not be able to submit a pull-request with their changes
 * @param ignoredFiles a list of path patterns to ignore during rendering
 * @param sections maps each section name to the folder containing the documents it contains
 * Sections whose folders are within those of other sections are rendered as sub-sections.
 * If a section path is ignored, it is discarded during site creation
 */
@Serializable
data class SiteConfiguration(
    val name: String,
    @SerialName("homepage") val homePage: String,
    @SerialName("expose-code") val exposesCode: Boolean = false,
    @SerialName("ignore") val ignoredFiles: List<String> = emptyList(),
    val sections: Map<String, String> = emptyMap()
)