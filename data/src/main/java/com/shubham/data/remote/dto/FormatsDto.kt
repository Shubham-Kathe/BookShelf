package com.shubham.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FormatsDto(
    @SerializedName("image/jpeg") var coverImage: String,
    @SerializedName("text/html") var html: String?,
    @SerializedName("application/pdf") var pdf: String?
)
