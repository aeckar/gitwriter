package model.vcs

import kotlinx.serialization.Serializable

@Serializable
data class Repository(val owner: String, val name: String) {
    override fun toString() = "$owner/$name"
}