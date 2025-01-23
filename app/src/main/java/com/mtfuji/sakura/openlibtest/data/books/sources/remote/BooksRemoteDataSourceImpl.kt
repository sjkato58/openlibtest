package com.mtfuji.sakura.openlibtest.data.books.sources.remote

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.data.books.sources.remote.api.BooksApi
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class BooksRemoteDataSourceImpl @Inject constructor(
    private val booksApi: BooksApi
): BooksRemoteDataSource {
    override fun getCurrentlyReading(): Observable<ApiBookResponse> {
        return booksApi.getCurrentlyReading()
    }

    override fun getWantToRead(): Observable<ApiBookResponse> {
        return booksApi.getWantToRead()
    }

    override fun getBookDetails(key: String): Observable<ApiBookDetailsModel> {
        return booksApi.getBookDetails(key)
    }
}