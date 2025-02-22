package com.mtfuji.sakura.openlibtest.data.books.sources.local

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import io.reactivex.rxjava3.core.Observable

interface BooksLocalDataSource {
    fun getCurrentlyReading(): Observable<ApiBookResponse>
    fun saveCurrentlyReading(apiBookResponse: ApiBookResponse)
    fun getWantToRead(): Observable<ApiBookResponse>
    fun saveWantToRead(apiBookResponse: ApiBookResponse)
    fun getBookDetails(key: String): Observable<ApiBookDetailsModel>
    fun saveBookDetails(key: String, apiBookDetailsModel: ApiBookDetailsModel)
}