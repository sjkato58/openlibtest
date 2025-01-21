package com.mtfuji.sakura.openlibtest.network.retrofit

import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.network.BOOKS_URL
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface OpenLibApi {
    @GET(value = "${BOOKS_URL}currently-reading.json")
    fun getCurrentlyReading(): Observable<ApiBookResponse>
    @GET(value = "want-to-read.json")
    fun getWantToRead(): Observable<ApiBookResponse>
}