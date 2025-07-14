package model.vcs

import kotlinx.serialization.Serializable
import model.File

/**
 * A file, folder, or commit in Git.
 * @param path the relative path of the file or folder
 * @param type if `"blob"`, this is a file. If `"tree"`, this is a folder, otherwise, is "commit" and this is a commit
 */
@Serializable
data class GitFile(val path: String, val type: String) : File {
    override val isFile = type == "blob"
    override val isFolder = type == "tree"
}