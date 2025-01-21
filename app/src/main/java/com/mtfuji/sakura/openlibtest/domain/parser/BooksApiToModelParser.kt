package com.mtfuji.sakura.openlibtest.domain.parser

import com.mtfuji.sakura.openlibtest.data.models.ApiBookModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.network.COVER_KEY
import com.mtfuji.sakura.openlibtest.network.COVER_KEY_VALUE
import com.mtfuji.sakura.openlibtest.network.COVER_SMALL_URL

fun ApiBookResponse.toModel(): List<BookModel> {
    return this.bookEntries.mapNotNull { apiBookEntryModel ->
        if (apiBookEntryModel.bookModel.isValid()) {
            BookModel(
                title = apiBookEntryModel.bookModel.title ?: "",
                key = apiBookEntryModel.bookModel.key,
                authorNames = apiBookEntryModel.bookModel.authorNames ?: listOf(),
                coverUrl = COVER_SMALL_URL.convertToCoverSmall(
                    coverKey = "id",
                    coverId = apiBookEntryModel.bookModel.coverId.toString()
                )
            )
        } else {
            null
        }
    }
}

fun ApiBookModel.isValid(): Boolean {
    return !(this.title.isNullOrBlank() && this.authorNames.isNullOrEmpty())
}

fun String.convertToCoverSmall(
    coverKey: String,
    coverId: String,
): String = this.replace(COVER_KEY, coverKey)
    .replace(COVER_KEY_VALUE, coverId)