package epm.xnox.topnews.data.mappers

import epm.xnox.topnews.data.sources.database.entities.ArticleEntity
import epm.xnox.topnews.domain.model.Article

fun ArticleEntity.toDomain() = Article(
    id = id,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source,
    title = title,
    url = url,
    urlToImage = urlToImage
)