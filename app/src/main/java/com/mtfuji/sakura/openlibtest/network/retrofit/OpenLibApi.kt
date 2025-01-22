package com.mtfuji.sakura.openlibtest.network.retrofit

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.network.BOOKS_DETAILS_URL
import com.mtfuji.sakura.openlibtest.network.BOOKS_URL
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibApi {
    @GET(value = "${BOOKS_URL}currently-reading.json")
    fun getCurrentlyReading(): Observable<ApiBookResponse>
    @GET(value = "${BOOKS_URL}want-to-read.json")
    fun getWantToRead(): Observable<ApiBookResponse>
    @GET(value = BOOKS_DETAILS_URL)
    fun getBookDetails(@Path(value = "book_key", encoded = true) key: String): Observable<ApiBookDetailsModel>
}