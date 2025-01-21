package com.mtfuji.sakura.openlibtest.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBookModel(
    @SerialName("title")
    val title: String?,
    @SerialName("key")
    val key: String,
    @SerialName("author_keys")
    val authorKeys: List<String>?,
    @SerialName("author_names")
    val authorNames: List<String>?,
    @SerialName("first_publish_year")
    val firstPublishYear: Int?,
    @SerialName("lending_edition_s")
    val lendingEditions: String?,
    @SerialName("edition_key")
    val editionKey: List<String>?,
    @SerialName("cover_id")
    val coverId: Int?,
    @SerialName("cover_edition_key")
    val coverEditionKey: String?
)