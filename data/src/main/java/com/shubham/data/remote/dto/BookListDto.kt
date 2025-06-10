package com.shubham.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookListDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: String,
    @SerializedName("results") var results: List<BookDto>
)