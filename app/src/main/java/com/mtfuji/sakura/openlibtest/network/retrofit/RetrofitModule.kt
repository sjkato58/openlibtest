package com.mtfuji.sakura.openlibtest.network.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providesRetrofitGenerator(): OpenLibRetrofitGenerator = OpenLibRetrofitGeneratorImpl()

    @Singleton
    @Provides
    fun providesRetrofit(
        openLibRetrofitGenerator: OpenLibRetrofitGenerator
    ): Retrofit = openLibRetrofitGenerator.execute()

    @Singleton
    @Provides
    fun provideMyBooksApi(
        retrofit: Retrofit
    ): OpenLibApi = retrofit.create(OpenLibApi::class.java)
}