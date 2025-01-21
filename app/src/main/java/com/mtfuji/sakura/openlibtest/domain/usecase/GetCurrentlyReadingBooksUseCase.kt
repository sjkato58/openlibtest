package com.mtfuji.sakura.openlibtest.domain.usecase

import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import io.reactivex.rxjava3.core.Observable

interface GetCurrentlyReadingBooksUseCase {
    fun execute(): Observable<List<BookModel>>
}