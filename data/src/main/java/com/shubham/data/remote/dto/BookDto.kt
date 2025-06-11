package com.shubham.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("authors") val authors: List<AuthorsDto>,
    @SerializedName("summaries") val summaries: List<String>,
    @SerializedName("formats") val formats: FormatsDto
)