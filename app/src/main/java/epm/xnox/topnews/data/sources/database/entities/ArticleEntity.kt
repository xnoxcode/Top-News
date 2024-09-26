package epm.xnox.topnews.data.sources.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import epm.xnox.topnews.domain.model.Source

@Entity(tableName = "article_table")
class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "source") val source: Source,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String
)