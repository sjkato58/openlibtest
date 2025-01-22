package com.mtfuji.sakura.openlibtest.data.books

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import io.reactivex.rxjava3.core.Observable

interface BooksRepository {
    fun getCurrentlyReading(): Observable<ApiBookResponse>
    fun getWantToRead(): Observable<ApiBookResponse>
    fun getBookDetails(key: String): Observable<ApiBookDetailsModel>
}