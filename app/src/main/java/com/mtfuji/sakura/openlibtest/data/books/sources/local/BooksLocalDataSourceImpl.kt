package com.mtfuji.sakura.openlibtest.data.books.sources.local

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

enum class CacheKey {
    CURRENTLYREADING,
    WANTTOREAD
}

class BooksLocalDataSourceImpl @Inject constructor(): BooksLocalDataSource {
    private val cachedData: MutableMap<CacheKey, ApiBookResponse> = mutableMapOf()
    private val cachedBookDetails: MutableMap<String, ApiBookDetailsModel> = mutableMapOf()

    override fun getCurrentlyReading(): Observable<ApiBookResponse> {
        return if (cachedData[CacheKey.CURRENTLYREADING] != null) {
            Observable.just(cachedData[CacheKey.CURRENTLYREADING]!!)
        } else {
            Observable.empty()
        }
    }

    override fun saveCurrentlyReading(apiBookResponse: ApiBookResponse) {
        cachedData[CacheKey.CURRENTLYREADING] = apiBookResponse
    }

    override fun getWantToRead(): Observable<ApiBookResponse> {
        return if (cachedData[CacheKey.WANTTOREAD] != null) {
            Observable.just(cachedData[CacheKey.WANTTOREAD]!!)
        } else {
            Observable.empty()
        }
    }

    override fun saveWantToRead(apiBookResponse: ApiBookResponse) {
        cachedData[CacheKey.WANTTOREAD] = apiBookResponse
    }

    override fun getBookDetails(key: String): Observable<ApiBookDetailsModel> {
        return if (cachedBookDetails[key] != null) {
            Observable.just(cachedBookDetails[key]!!)
        } else {
            Observable.empty()
        }
    }

    override fun saveBookDetails(key: String, apiBookDetailsModel: ApiBookDetailsModel) {
        cachedBookDetails[key] = apiBookDetailsModel
    }
}