package com.mtfuji.sakura.openlibtest.domain.usecase

import com.mtfuji.sakura.openlibtest.data.books.BooksRepository
import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.domain.models.BookDetailsModel
import com.mtfuji.sakura.openlibtest.domain.parser.locateBook
import com.mtfuji.sakura.openlibtest.domain.parser.toModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetBookDetailsUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository
): GetBookDetailsUseCase {
    override fun execute(key: String): Observable<BookDetailsModel> {
        return booksRepository.getBookDetails(key).flatMap { apiBookDetailsModel: ApiBookDetailsModel ->
            booksRepository.getCurrentlyReading().map { apiBookResponse: ApiBookResponse ->
                val bookEntry = apiBookResponse.locateBook(key)
                apiBookDetailsModel.toModel(bookEntry?.bookModel)
            }
        }.retry(0)
            .doOnError { it.printStackTrace() }
    }
}