package epm.xnox.topnews.presentation.search.ui

import epm.xnox.topnews.domain.model.Article

data class SearchState(
    val error: String = "",
    val loading: Boolean = false,
    val data: List<Article> = emptyList()
)