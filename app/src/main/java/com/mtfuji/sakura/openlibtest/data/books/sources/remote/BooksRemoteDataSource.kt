package com.mtfuji.sakura.openlibtest.data.books.sources.remote

import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import io.reactivex.rxjava3.core.Observable

interface BooksRemoteDataSource {
    fun getCurrentlyReading(): Observable<ApiBookResponse>
    fun getWantToRead(): Observable<ApiBookResponse>
}