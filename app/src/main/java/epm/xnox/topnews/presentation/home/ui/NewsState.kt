package epm.xnox.topnews.presentation.home.ui

import epm.xnox.topnews.domain.model.Article

data class NewsState(
    val error: String = "",
    val loading: Boolean = false,
    val data: List<Article> = emptyList()
)
