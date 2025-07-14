package model.content

import kotlinx.serialization.Serializable
import model.File

/**
 * A folder or file.
 * @param name the name of the folder or file
 * @param children if not null, contains the files and folders held by this folder.
 * Otherwise, this object represents a file
 */
@Serializable
data class ContentFile(
    val name: String,
    val children: MutableList<ContentFile>? = mutableListOf()
) : File {
    /** Returns true if this is a file. */
    override val isFile = children == null

    /** Returns true if this is a folder. */
    override val isFolder = children != null

    /**
     * Returns the list of files or folders contained by this folder.
     * @throws NoSuchElementException this is not a folder
     */
    fun children(): MutableList<ContentFile> {
        return children ?: throw NoSuchElementException("File '$name' cannot have children")
    }

    override fun toString(): String {
        if (children != null) {
            return "$name $children"
        }
        return name
    }
}