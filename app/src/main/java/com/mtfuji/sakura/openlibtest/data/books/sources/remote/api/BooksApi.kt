package com.mtfuji.sakura.openlibtest.data.books.sources.remote.api

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.network.BOOKS_DETAILS_URL
import com.mtfuji.sakura.openlibtest.network.BOOKS_URL
import com.mtfuji.sakura.openlibtest.network.BOOK_KEY_REF
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksApi {
    @GET(value = "${BOOKS_URL}currently-reading.json")
    fun getCurrentlyReading(): Observable<ApiBookResponse>
    @GET(value = "${BOOKS_URL}want-to-read.json")
    fun getWantToRead(): Observable<ApiBookResponse>
    @GET(value = BOOKS_DETAILS_URL)
    fun getBookDetails(@Path(value = BOOK_KEY_REF, encoded = true) key: String): Observable<ApiBookDetailsModel>
}