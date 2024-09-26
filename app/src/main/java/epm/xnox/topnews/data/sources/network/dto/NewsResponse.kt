package epm.xnox.topnews.data.sources.network.dto

import epm.xnox.topnews.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)