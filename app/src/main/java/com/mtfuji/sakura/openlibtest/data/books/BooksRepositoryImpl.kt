package com.mtfuji.sakura.openlibtest.data.books

import com.mtfuji.sakura.openlibtest.data.books.sources.local.BooksLocalDataSource
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.BooksRemoteDataSource
import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val localDataSource: BooksLocalDataSource,
    private val remoteDataSource: BooksRemoteDataSource
): BooksRepository {
    override fun getCurrentlyReading(): Observable<ApiBookResponse> {
        return localDataSource.getCurrentlyReading()
            .switchIfEmpty(
                remoteDataSource.getCurrentlyReading()
                    .doOnNext { apiBookResponse ->
                        localDataSource.saveCurrentlyReading(apiBookResponse)
                    }
            )
    }

    override fun getWantToRead(): Observable<ApiBookResponse> {
        return localDataSource.getWantToRead()
            .switchIfEmpty(
                remoteDataSource.getWantToRead()
                    .doOnNext { apiBookResponse ->
                        localDataSource.saveWantToRead(apiBookResponse)
                    }
            )
    }

    override fun getBookDetails(key: String): Observable<ApiBookDetailsModel> {
        return localDataSource.getBookDetails(key)
            .switchIfEmpty(
                remoteDataSource.getBookDetails(key)
                    .doOnNext { apiBookDetailsModel ->
                        localDataSource.saveBookDetails(key, apiBookDetailsModel)
                    }
            )
    }
}