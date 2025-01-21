package com.mtfuji.sakura.openlibtest.domain.parser

import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.network.COVER_KEY
import com.mtfuji.sakura.openlibtest.network.COVER_KEY_VALUE
import com.mtfuji.sakura.openlibtest.network.COVER_SMALL_URL

fun ApiBookResponse.toModel(): List<BookModel> {
    return this.bookEntries.map { apiBookEntryModel ->
        BookModel(
            title = apiBookEntryModel.bookModel.title,
            key = apiBookEntryModel.bookModel.key,
            authorNames = apiBookEntryModel.bookModel.authorNames,
            coverUrl = COVER_SMALL_URL.convertToCoverSmall(
                coverKey = "id",
                coverId = apiBookEntryModel.bookModel.coverId.toString()
            )
        )
    }
}

fun String.convertToCoverSmall(
    coverKey: String,
    coverId: String,
): String = this
    .replace(COVER_KEY, coverKey)
    .replace(COVER_KEY_VALUE, coverId)