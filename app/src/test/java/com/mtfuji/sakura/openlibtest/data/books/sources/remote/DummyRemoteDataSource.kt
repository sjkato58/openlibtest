package com.mtfuji.sakura.openlibtest.data.books.sources.remote

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import io.reactivex.rxjava3.core.Observable
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.TimeUnit

class DummyRemoteDataSource: BooksRemoteDataSource {

    private var currentlyReadingApi: ApiBookResponse? = null
    private var wantToReadyApi: ApiBookResponse? = null
    private var bookDetailsModelApi: ApiBookDetailsModel? = null

    private var apiBookResponseError: String = ""
    private var apiBookDetailsError: String = ""

    @TestOnly
    fun setCurrentlyReading(bookResponse: ApiBookResponse?) {
        currentlyReadingApi = bookResponse
    }

    @TestOnly
    fun setWantToRead(bookResponse: ApiBookResponse?) {
        wantToReadyApi = bookResponse
    }

    @TestOnly
    fun setBookDetails(bookDetailsModel: ApiBookDetailsModel?) {
        bookDetailsModelApi = bookDetailsModel
    }

    @TestOnly
    fun setApiBookResponseError(errorMessage: String) {
        apiBookResponseError = errorMessage
    }

    @TestOnly
    fun setApiBookDetailsError(errorMessage: String) {
        apiBookDetailsError = errorMessage
    }

    @TestOnly
    fun refresh() {
        setCurrentlyReading(null)
        setWantToRead(null)
        setBookDetails(null)
        setApiBookResponseError("")
        setApiBookDetailsError("")
    }

    override fun getCurrentlyReading(): Observable<ApiBookResponse> {
        return if (apiBookResponseError.isNotBlank()) {
            Observable.timer(1, TimeUnit.SECONDS)
                .flatMap { Observable.error(Throwable(apiBookResponseError)) }
        } else if (currentlyReadingApi != null) {
            Observable.just(currentlyReadingApi!!)
        } else Observable.empty()
    }

    override fun getWantToRead(): Observable<ApiBookResponse> {
        return if (apiBookResponseError.isNotBlank()) {
            Observable.timer(1, TimeUnit.SECONDS)
                .flatMap { Observable.error(Throwable(apiBookResponseError)) }
        } else if (wantToReadyApi != null) {
            Observable.just(wantToReadyApi!!)
        } else Observable.empty()
    }

    override fun getBookDetails(key: String): Observable<ApiBookDetailsModel> {
        return if (apiBookDetailsError.isNotBlank()) {
            Observable.timer(1, TimeUnit.SECONDS)
                .flatMap { Observable.error(Throwable(apiBookDetailsError)) }
        } else if (bookDetailsModelApi != null) {
            Observable.just(bookDetailsModelApi!!)
        } else Observable.empty()
    }
}