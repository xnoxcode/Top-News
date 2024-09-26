package epm.xnox.topnews.domain.repository

import epm.xnox.topnews.data.sources.database.entities.ArticleEntity
import epm.xnox.topnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insertArticle(article: ArticleEntity)
    suspend fun getAllArticles(): Flow<List<Article>>
    suspend fun deleteArticle(id: Int)
    suspend fun deleteAllArticles()

}