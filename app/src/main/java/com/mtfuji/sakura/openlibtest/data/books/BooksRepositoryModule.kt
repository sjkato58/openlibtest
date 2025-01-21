package com.mtfuji.sakura.openlibtest.data.books

import com.mtfuji.sakura.openlibtest.data.books.sources.local.BooksLocalDataSource
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.BooksRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BooksRepositoryModule {

    @Singleton
    @Provides
    fun providesBooksRepository(
        booksLocalDataSource: BooksLocalDataSource,
        booksRemoteDataSource: BooksRemoteDataSource
    ): BooksRepository = BooksRepositoryImpl(
        booksLocalDataSource, booksRemoteDataSource
    )
}