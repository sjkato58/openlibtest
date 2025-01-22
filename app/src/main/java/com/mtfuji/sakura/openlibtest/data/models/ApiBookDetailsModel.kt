package com.mtfuji.sakura.openlibtest.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBookDetailsModel(
    @SerialName("key")
    val key: String,
    @SerialName("title")
    val title: String,
    @SerialName("first_publish_date")
    val firstPublishDate: String? = null,
    @SerialName("authors")
    val authors: List<Author>? = null,
    @SerialName("type")
    val type: BookType,
    @SerialName("description")
    val description: Description? = null,
    @SerialName("covers")
    val covers: List<Int>? = null,
    @SerialName("lc_classifications")
    val lcClassifications: List<String>? = null,
    @SerialName("dewey_number")
    val deweyNumber: List<String>? = null,
    @SerialName("subjects")
    val subjects: List<String>? = null,
    @SerialName("first_sentence")
    val firstSentence: FirstSentence? = null,
    @SerialName("subject_times")
    val subjectTimes: List<String>? = null,
    @SerialName("latest_revision")
    val latestRevision: Int? = null,
    @SerialName("revision")
    val revision: Int? = null,
    @SerialName("created")
    val created: Created? = null,
    @SerialName("last_modified")
    val lastModified: LastModified? = null
)

@Serializable
data class Author(
    @SerialName("author")
    val author: AuthorDetails,
    @SerialName("type")
    val type: AuthorType
)

@Serializable
data class AuthorDetails(
    @SerialName("key")
    val key: String
)

@Serializable
data class AuthorType(
    @SerialName("key")
    val key: String
)

@Serializable
data class BookType(
    @SerialName("key")
    val key: String
)

@Serializable
data class Description(
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)

@Serializable
data class FirstSentence(
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)

@Serializable
data class Created(
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)

@Serializable
data class LastModified(
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)