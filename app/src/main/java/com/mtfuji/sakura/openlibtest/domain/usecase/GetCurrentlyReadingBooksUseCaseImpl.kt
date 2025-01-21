package com.mtfuji.sakura.openlibtest.domain.usecase

import com.mtfuji.sakura.openlibtest.data.books.BooksRepository
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.domain.parser.toModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetCurrentlyReadingBooksUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository
): GetCurrentlyReadingBooksUseCase {
    override fun execute(): Observable<List<BookModel>> {
        return booksRepository.getCurrentlyReading()
            .map { apiBookResponse ->
                apiBookResponse.toModel()
            }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.error(throwable)
            }
    }
}