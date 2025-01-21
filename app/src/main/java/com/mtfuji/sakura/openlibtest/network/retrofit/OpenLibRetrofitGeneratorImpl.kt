package com.mtfuji.sakura.openlibtest.network.retrofit

import com.mtfuji.sakura.openlibtest.network.BOOKS_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class OpenLibRetrofitGeneratorImpl: OpenLibRetrofitGenerator {
    override fun execute(): Retrofit = Retrofit.Builder()
        .baseUrl(BOOKS_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}