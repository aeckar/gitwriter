package model

import kotlinx.serialization.Serializable

/**
 * Describes a markup language that can be rendered as a webpage.
 * @param extensions file extensions containing this language
 * @param color hexadecimal color value used to represent language in GitHub, if one exists
 * @see markupLanguages
 */
@Serializable
data class MarkupLanguage(val extensions: List<String>, val color: String? = null)