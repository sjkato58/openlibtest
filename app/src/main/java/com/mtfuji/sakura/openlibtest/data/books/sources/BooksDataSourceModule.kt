package com.mtfuji.sakura.openlibtest.data.books.sources

import com.mtfuji.sakura.openlibtest.data.books.sources.local.BooksLocalDataSource
import com.mtfuji.sakura.openlibtest.data.books.sources.local.BooksLocalDataSourceImpl
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.BooksRemoteDataSource
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.BooksRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BooksDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindBooksLocalDataSource(
        booksLocalDataSourceImpl: BooksLocalDataSourceImpl
    ): BooksLocalDataSource

    @Singleton
    @Binds
    abstract fun bindBooksRemoteDataSource(
        booksRemoteDataSourceImpl: BooksRemoteDataSourceImpl
    ): BooksRemoteDataSource
}