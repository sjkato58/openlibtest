package com.mtfuji.sakura.openlibtest.domain.parser

import com.mtfuji.sakura.openlibtest.data.models.ApiBookDetailsModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookEntryModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookModel
import com.mtfuji.sakura.openlibtest.data.models.ApiBookResponse
import com.mtfuji.sakura.openlibtest.domain.models.BookDetailsModel
import com.mtfuji.sakura.openlibtest.domain.models.BookModel
import com.mtfuji.sakura.openlibtest.network.COVER_KEY
import com.mtfuji.sakura.openlibtest.network.COVER_KEY_VALUE
import com.mtfuji.sakura.openlibtest.network.COVER_MEDIUM_URL
import com.mtfuji.sakura.openlibtest.network.COVER_SMALL_URL

fun ApiBookResponse.toModel(): List<BookModel> {
    return this.bookEntries.mapNotNull { apiBookEntryModel ->
        if (apiBookEntryModel.bookModel.isValid()) {
            BookModel(
                title = apiBookEntryModel.bookModel.title ?: "",
                key = apiBookEntryModel.bookModel.key,
                authorNames = apiBookEntryModel.bookModel.authorNames ?: listOf(),
                coverUrl = COVER_SMALL_URL.convertToCover(
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

fun String.convertToCover(
    coverKey: String,
    coverId: String,
): String = this.replace(COVER_KEY, coverKey)
    .replace(COVER_KEY_VALUE, coverId)

fun ApiBookResponse.locateBook(key: String): ApiBookEntryModel? {
    val list = this.bookEntries.filter { it.bookModel.key == key }
    return if (list.isNotEmpty()) {
        list[0]
    } else {
        null
    }
}

fun ApiBookDetailsModel.toModel(apiBookModel: ApiBookModel?): BookDetailsModel = BookDetailsModel(
        key = this.key,
        title = this.title,
        firstPublishedData = this.firstPublishDate ?: "",
        authors = apiBookModel?.authorNames ?: listOf(),
        description = this.getDescription() ?: "",
        covers = this.covers?.extractCoverUrls() ?: listOf(),
        subjects = this.subjects ?: listOf(),
        firstSentence = this.firstSentence?.value ?: "",
        latestRevision = this.latestRevision ?: -1,
    )

fun List<Int>.extractCoverUrls(): List<String> = this.map {
    COVER_MEDIUM_URL.convertToCover("id", it.toString())
}