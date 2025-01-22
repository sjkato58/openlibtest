package com.mtfuji.sakura.openlibtest.domain.usecase

import com.mtfuji.sakura.openlibtest.domain.models.BookDetailsModel
import io.reactivex.rxjava3.core.Observable

interface GetBookDetailsUseCase {
    fun execute(key: String): Observable<BookDetailsModel>
}