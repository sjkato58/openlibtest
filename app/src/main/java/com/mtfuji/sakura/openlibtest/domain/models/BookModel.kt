package com.mtfuji.sakura.openlibtest.domain.models

data class BookModel(
    val title: String,
    val key: String,
    val authorNames: List<String>,
    val coverUrl: String
)