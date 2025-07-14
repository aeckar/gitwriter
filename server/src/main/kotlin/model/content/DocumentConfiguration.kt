package model.content

import io.ktor.client.call.*
import io.ktor.client.statement.bodyAsText
import io.ktor.server.plugins.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.content
import model.decodeJson
import model.fileName
import model.github
import model.orResourceNotFound
import model.parentDirectory
import model.vcs.GitFileTree
import model.vcs.Repository

/**
 * Contains information on the document as given by a configuration file.
 *
 * Every repository eligible to be converted into a document must contain a (possibly empty) `site.toml` file,
 * which contains this configuration. Instances of this class are deserialized from such files.
 *
 * Except in the case of deserialization, this class should never be instantiated directly.
 * Instead, the [get][Document.Companion.get] method should be used to obtain a specific instance.
 *
 * Special conditions:
 * - If `icon` is empty, a default image is used
 * - If `favicon` is empty, a default image is used
 * @param icon the icon that shows in the navigation bar to the left of the document title
 * @param favicon the tab icon used for each page in the document
 * @param contentRoot the relative path in the repository where document contents can be found
 * @param contentGroups maps folders, by relative path, to their group name
 */
@Serializable
data class DocumentConfiguration(
    val title: String = "Untitled Document",
    val icon: String = "",
    val favicon: String = "",
    val contentRoot: String = "/",
    val contentGroups: Map<String, String> = emptyMap(),
) {
    private val document: Document? = null

    suspend fun toDocument(repo: Repository): Document {
        if (document != null) {
            return document
        }
        return Document(
            title,
            icon,
            favicon,
            contentRoot,
            contentGroups,
            repo,
            homePath(repo),
            contentDirectory(repo)
        )
    }

    /** @see Document.homePath */
    private suspend fun homePath(repo: Repository): String {
        val homePaths = fileExtensions.asFlow()
            .map { ext ->
                val path = "$contentRoot/index.$ext"
                val content = content(repo, path)
                content?.let { path }
            }
            .filterNotNull()
            .toList()
        if (homePaths.isEmpty()) {
            throw NotFoundException("Home page 'index.$fileExtensions' not found in content root '$contentRoot'")
        }
        if (homePaths.size > 1) {
            val localPaths = homePaths.map { it.removePrefix(contentRoot) }
            throw IllegalStateException("Multiple home pages found for content root '$contentRoot': $localPaths")
        }
        return homePaths.single()
    }

    /** @see Document.contentDirectory */
    private suspend fun contentDirectory(repo: Repository): ContentFile {
        val defaultBranch = github("repos/$repo")
            .orResourceNotFound { "Repository '$repo'" }
            .bodyAsText()
            .decodeJson()
            .getValue("default_branch").jsonPrimitive.content
        val commit = github("repos/$repo/branches/$defaultBranch")
            .orResourceNotFound { "Tree SHA for branch '$defaultBranch' in repository '$repo'" }
            .bodyAsText()
            .decodeJson()
            .getValue("commit").jsonObject
            .getValue("sha").jsonPrimitive.content
        val tree = github("repos/$repo/git/trees/$commit?recursive=1")
            .orResourceNotFound { "Git tree for branch '$defaultBranch' in repository '$repo'" }
            .body<GitFileTree>()
        // todo handle truncated tree
        val root = ContentFile("<root>")
        tree.nodes.asSequence()
            .groupBy { item -> item.path.parentDirectory() }
            .forEach { (parentDir, items) ->
                val segments = parentDir.split('/')
                var parent = root
                if (segments[0] != "") {    // Assumes path does not start with '/'
                    for (segment in segments) { // Build directory up to parent folder
                        if (parent.children().none { (name) -> name == segment }) {
                            val child = ContentFile(segment)
                            parent.children() += child
                            parent = child
                        } else {
                            parent = parent.children().first { (name) -> name == segment }
                        }
                    }
                }
                for (item in items) {
                    if (item.isFile && fileExtensions.any { ext -> item.path.endsWith(".$ext") }) {
                        parent.children() += ContentFile(item.path.fileName(), null)
                    } else if (item.isFolder) { // Ensure empty folders are including in directory
                        val folderName = item.path.fileName()
                        if (parent.children().none { (name) -> name != folderName }) {
                            parent.children() += ContentFile(folderName)
                        }
                    }   // Otherwise, is a commit--ignore these
                }
            }
        return root
    }

    companion object {
        private val fileExtensions = listOf("md", "ft")
    }
}