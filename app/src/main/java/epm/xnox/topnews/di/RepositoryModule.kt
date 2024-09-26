package epm.xnox.topnews.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import epm.xnox.topnews.data.repository.DatabaseRepositoryImpl
import epm.xnox.topnews.data.repository.NetworkRepositoryImpl
import epm.xnox.topnews.data.repository.PreferenceRepositoryImpl
import epm.xnox.topnews.data.sources.database.dao.ArticleDao
import epm.xnox.topnews.data.sources.network.api.ApiService
import epm.xnox.topnews.domain.repository.DatabaseRepository
import epm.xnox.topnews.domain.repository.NetworkRepository
import epm.xnox.topnews.domain.repository.PreferenceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: ApiService): NetworkRepository =
        NetworkRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: ArticleDao): DatabaseRepository = DatabaseRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePreferenceRepository(@ApplicationContext context: Context): PreferenceRepository =
        PreferenceRepositoryImpl(context)
}