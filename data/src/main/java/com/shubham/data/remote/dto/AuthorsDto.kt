package com.shubham.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthorsDto(
    @SerializedName("name") var name: String
)