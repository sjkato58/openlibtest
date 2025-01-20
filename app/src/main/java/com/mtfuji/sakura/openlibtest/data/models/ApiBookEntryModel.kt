package com.mtfuji.sakura.openlibtest.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBookEntryModel(
    @SerialName("work")
    val bookModel: ApiBookModel,
    @SerialName("logged_edition")
    val loggedEdition: String?,
    @SerialName("logged_date")
    val loggedDate: String
)
