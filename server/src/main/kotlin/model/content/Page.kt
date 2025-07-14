package model.content

import kotlinx.serialization.Serializable

@Serializable
data class Page(val site: Document, val content: String) {
    override fun toString(): String = "(page in $site)"
}