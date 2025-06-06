package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param extensions file extensions containing this language
 * @param textMateScope the scope of the TextMate grammar belonging to this language.
 * TextMate grammars for each language can be found at todo
 * @param color hexadecimal color value used to represent language in GitHub, if one exists
 * @param isHumanReadable true if the language is human-readable
 */
@Serializable
data class LanguageConfiguration(
    val extensions: List<String>,
    @SerialName("tm_scope") val textMateScope: String,
    val color: String? = null,
    val isHumanReadable: Boolean = true
)