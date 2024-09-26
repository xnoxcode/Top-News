package epm.xnox.topnews.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import epm.xnox.topnews.data.sources.database.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, Database::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNoteDao(db: Database) = db.getNotesDao()
}