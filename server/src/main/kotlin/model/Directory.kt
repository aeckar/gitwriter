package model

import kotlinx.serialization.Serializable

@Serializable
data class Directory(val name: String, val children: List<Directory>? = null) {
    override fun toString(): String {
        if (children != null) {
            return "$name $children"
        }
        return name
    }
}