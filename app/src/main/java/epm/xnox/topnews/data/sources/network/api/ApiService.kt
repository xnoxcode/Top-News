package epm.xnox.topnews.data.sources.network.api

import epm.xnox.topnews.core.common.Constant.SEARCH_EVERYTHING
import epm.xnox.topnews.core.common.Constant.SEARCH_TOP_HEADLINE
import epm.xnox.topnews.data.sources.network.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH_EVERYTHING)
    suspend fun getAllNews(
        @Query("q") world: String,
        @Query("from") date: String,
        @Query("language") language: String = "es"
    ) : NewsResponse

    @GET(SEARCH_TOP_HEADLINE)
    suspend fun getTopNews() : NewsResponse
}