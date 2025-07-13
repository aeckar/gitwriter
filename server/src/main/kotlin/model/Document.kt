package model

import kotlinx.serialization.Serializable
import java.util.concurrent.ConcurrentHashMap

@Serializable
data class Document(
    val repo: Repository,
    val icon: String,
    val favicon: String,
    val contentRoot: String,
    val contentDirectory: NavigableTree,
    val folderAliases: Map<String, String>
) {
    private val homePage: String
    private val pages: MutableMap<String, String> = ConcurrentHashMap()

    init {
        //search in repo, if any index.* is not markup or multiple index.*, throw

    }

    suspend operator fun get(path: String): String {
        if (path in pages) {
            return pages.getValue(path)
        }
        val content = content(repo, path)
    }

    companion object {
        private val cache: MutableMap<Repository, Document> = ConcurrentHashMap()

        suspend fun get(repo: Repository): Document {
            if (repo in cache) {
                return cache.getValue(repo)
            }
            val config = content(repo, "site.toml")

        }
    }
}