package model.vcs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The directory tree of a Git repository.
 * @param nodes the list of unique files, folders, and commits in a repository
 * @param truncated if true, the tree was truncated due to the size of the repository
 */
@Serializable
data class GitFileTree(@SerialName("tree") val nodes: List<GitFile>, val truncated: Boolean)
