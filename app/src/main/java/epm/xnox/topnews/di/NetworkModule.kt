package epm.xnox.topnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import epm.xnox.topnews.core.common.Constant.BASE_URL
import epm.xnox.topnews.data.sources.network.api.ApiService
import epm.xnox.topnews.data.sources.network.interceptor.HeaderInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getBaseUrl(): String = BASE_URL

    @Singleton
    @Provides
    fun getInterceptor(): Interceptor = HeaderInterceptor()

    @Singleton
    @Provides
    fun getRetrofitClient(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun getApiCLient(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun getClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
}