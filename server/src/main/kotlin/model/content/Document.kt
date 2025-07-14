package model.content

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.source.decodeFromStream
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.serialization.Serializable
import model.content
import model.orResourceNotFound
import model.vcs.Repository
import java.util.concurrent.ConcurrentHashMap

/**
 * Contains information on the document contained by a repository.
 *
 * Instances of this class are sent to the client over HTTP.
 * @param title the title of the document
 * @param repo the user and name assigned to the GitHub repository
 * @param homePath the relative path of the home page (`index.*`)
 * @param contentDirectory a directory of all content, organized by group. Empty groups are discarded
 * @see DocumentConfiguration.icon
 * @see DocumentConfiguration.favicon
 * @see DocumentConfiguration.contentRoot
 * @see DocumentConfiguration.contentGroups
 */
@Serializable
data class Document(
    val title: String,
    val icon: String,
    val favicon: String,
    val contentRoot: String,
    val contentGroups: Map<String, String>,
    val repo: Repository,
    val homePath: String,
    val contentDirectory: ContentFile
) {
    private val pages: MutableMap<String, String> = ConcurrentHashMap()

    /**
     * Returns the page with the given relative path.
     *
     * If provided an empty string, returns the home page.
     */
    suspend operator fun get(path: String): String {
        if (path in pages) {
            return pages.getValue(path)
        }
        val file = content(repo, path.ifEmpty { homePath }).orResourceNotFound { "Page '$path'" }
        val page = file.readRemaining().readText()  // TODO parser entry point
        pages[path] = page
        return page
    }

    companion object {
        private val cache: MutableMap<Repository, Document> = ConcurrentHashMap()

        /** Returns the document with the given user and repository name. */
        suspend fun get(repo: Repository): Document {
            if (repo in cache) {
                return cache.getValue(repo)
            }
            val config = content(repo, "site.toml") ?: ByteReadChannel("")  // Use defaults if not configured
            val document = Toml
                .decodeFromStream<DocumentConfiguration>(config.toInputStream(null))
                .toDocument(repo)
            cache[repo] = document
            return document
        }
    }
}