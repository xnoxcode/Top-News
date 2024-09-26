package epm.xnox.topnews.domain.repository

import epm.xnox.topnews.core.common.Result
import epm.xnox.topnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getAllNews(world: String, date: String): Flow<Result<List<Article>>>
    suspend fun getTopNews(): Flow<Result<List<Article>>>
}