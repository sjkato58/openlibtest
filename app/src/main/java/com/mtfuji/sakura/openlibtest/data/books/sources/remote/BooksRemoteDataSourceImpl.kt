package com.mtfuji.sakura.openlibtest.data.books.sources.remote

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.network.retrofit.OpenLibApi
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class BooksRemoteDataSourceImpl @Inject constructor(
    private val openLibApi: OpenLibApi
): BooksRemoteDataSource {
    override fun getCurrentlyReading(): Observable<ApiBookResponse> {
        return openLibApi.getCurrentlyReading()
    }

    override fun getWantToRead(): Observable<ApiBookResponse> {
        return openLibApi.getWantToRead()
    }

    override fun getBookDetails(key: String): Observable<ApiBookDetailsModel> {
        return openLibApi.getBookDetails(key)
    }
}