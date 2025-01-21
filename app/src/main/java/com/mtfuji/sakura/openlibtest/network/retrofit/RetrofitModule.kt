package com.mtfuji.sakura.openlibtest.network.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofitGenerator(): OpenLibRetrofitGenerator = OpenLibRetrofitGeneratorImpl()

    @Provides
    @Singleton
    fun providesRetrofit(
        openLibRetrofitGenerator: OpenLibRetrofitGenerator
    ): Retrofit = openLibRetrofitGenerator.execute()

    @Provides
    @Singleton
    fun provideMyBooksApi(
        retrofit: Retrofit
    ): OpenLibApi = retrofit.create(OpenLibApi::class.java)
}