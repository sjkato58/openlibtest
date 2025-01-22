package com.mtfuji.sakura.openlibtest.domain.models

data class BookDetailsModel(
    val key: String,
    val title: String,
    val firstPublishedData: String,
    val authors: List<String>,
    val description: String,
    val covers: List<String>,
    val subjects: List<String>,
    val firstSentence: String,
    val subjectTimes: String,
    val latestRevision: Int
)