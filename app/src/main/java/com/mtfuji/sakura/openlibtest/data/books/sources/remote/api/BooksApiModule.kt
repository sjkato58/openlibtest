package com.mtfuji.sakura.openlibtest.data.books.sources.remote.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BooksApiModule {

    @Singleton
    @Provides
    fun provideBooksApi(
        retrofit: Retrofit
    ): BooksApi = retrofit.create(BooksApi::class.java)
}