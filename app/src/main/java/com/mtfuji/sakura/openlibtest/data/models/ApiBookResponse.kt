package com.mtfuji.sakura.openlibtest.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBookResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("numFound")
    val numFound: Int,
    @SerialName("reading_log_entries")
    val bookEntries: List<ApiBookEntryModel>
)
