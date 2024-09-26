package epm.xnox.topnews.data.sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import epm.xnox.topnews.data.sources.database.converters.Converters
import epm.xnox.topnews.data.sources.database.dao.ArticleDao
import epm.xnox.topnews.data.sources.database.entities.ArticleEntity

@TypeConverters(value = [Converters::class])
@Database(entities = [ArticleEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun getNotesDao(): ArticleDao
}