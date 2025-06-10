package com.shubham.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val summary: String,
    val coverImageUrl: String?,
    val previewUrl: String?
)