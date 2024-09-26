package epm.xnox.topnews.data.repository

import epm.xnox.topnews.data.mappers.toDomain
import epm.xnox.topnews.data.sources.database.dao.ArticleDao
import epm.xnox.topnews.data.sources.database.entities.ArticleEntity
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val dao: ArticleDao) : DatabaseRepository {

    override suspend fun insertArticle(article: ArticleEntity) {
        dao.insertArticle(article)
    }

    override suspend fun getAllArticles(): Flow<List<Article>> = flow {
        val articles = dao.getAllArticles().map {
            it.map { articleEntity ->
                articleEntity.toDomain()
            }
        }
        emitAll(articles)
    }

    override suspend fun deleteArticle(id: Int) {
        dao.deleteArticle(id)
    }

    override suspend fun deleteAllArticles() {
        dao.deleteAllArticles()
    }
}