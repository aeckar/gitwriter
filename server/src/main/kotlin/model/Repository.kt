package model

import kotlinx.serialization.Serializable

@Serializable
data class Repository(val user: String, val name: String) {
    override fun toString() = "$user/$name"
}