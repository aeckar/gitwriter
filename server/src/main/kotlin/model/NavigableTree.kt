package model

import kotlinx.serialization.Serializable

@Serializable
data class NavigableTree(val name: String, val children: List<NavigableTree>? = null) {
    override fun toString(): String {
        if (children != null) {
            return "$name $children"
        }
        return name
    }
}