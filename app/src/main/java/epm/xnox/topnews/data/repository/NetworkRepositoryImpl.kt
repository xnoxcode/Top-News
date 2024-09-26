package epm.xnox.topnews.data.repository

import epm.xnox.topnews.core.common.Constant.UNKNOW_ERROR
import epm.xnox.topnews.core.common.Result
import epm.xnox.topnews.data.sources.network.api.ApiService
import epm.xnox.topnews.data.sources.network.dto.NewsResponse
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.domain.model.Source
import epm.xnox.topnews.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val api: ApiService) : NetworkRepository {

    override suspend fun getAllNews(world: String, date: String): Flow<Result<List<Article>>> =
        flow {
            emit(Result.Loading)
            try {
                val data: NewsResponse = api.getAllNews(world, date)
                val articles: List<Article> = data.articles.map { clearNullField(it) }
                emit(Result.Success(articles))
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: UNKNOW_ERROR))
            }
        }

    override suspend fun getTopNews(): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading)
        try {
            val data: NewsResponse = api.getTopNews()
            emit(Result.Success(data.articles))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: UNKNOW_ERROR))
        }
    }

    private fun clearNullField(article: Article): Article {
        return article.copy(
            author = article.author ?: "---",
            content = article.content ?: "---",
            description = article.description ?: "---",
            publishedAt = article.publishedAt ?: "---",
            title = article.title ?: "---",
            url = article.url ?: "---",
            urlToImage = article.urlToImage ?: "---",
            source = article.source.let {
                Source(
                    id = it.id ?: "---",
                    name = it.name ?: "---"
                )
            }
        )
    }
}
