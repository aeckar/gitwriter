package model

data class GithubSearchQuery(val searchTerms: List<String>, val filters: Map<String, String>) {
    override fun toString(): String {
        val searchTerms = searchTerms.joinToString("+")
        val filters = filters.entries.joinToString("+") { (filter, value) -> "$filter:$value" }
        return "$searchTerms+$filters"
    }

    companion object {
        fun parse(query: String): GithubSearchQuery {
            val searchTerms = mutableListOf<String>()
            val filters = mutableMapOf<String, String>()
            var termIndex = -1
            for ((index, c) in query.withIndex()) {
                if (termIndex != -1 && c.isWhitespace()) {
                    val searchTerm = query.substring(termIndex, index)
                    val colonIndex = searchTerm.asSequence()
                        .map { it == ':' }
                        .withIndex()
                        .singleOrNull { (index, isColon) -> isColon && index in 1..searchTerm.lastIndex - 1 }
                    if (colonIndex == null) {
                        searchTerms += searchTerm
                    } else {
                        searchTerm.split(':')
                            .let { (filter, value) -> filters[filter] = value }
                    }
                    termIndex = -1
                    continue
                }
                if (c.isWhitespace()) {
                    continue
                }
                if (termIndex == -1) {
                    termIndex = index
                }
            }
            return GithubSearchQuery(searchTerms, filters)
        }
    }
}