package model

import io.ktor.server.plugins.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Serializable

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
    private suspend fun contentDirectory(repo: Repository): Directory {

    }

    private companion object {
        val fileExtensions = listOf(".md", ".ft")
    }
}