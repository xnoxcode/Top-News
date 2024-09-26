package epm.xnox.topnews.data.sources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import epm.xnox.topnews.data.sources.database.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM article_table order by publishedAt DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticles()

    @Query("DELETE FROM article_table WHERE id = :id")
    suspend fun deleteArticle(id: Int)
}