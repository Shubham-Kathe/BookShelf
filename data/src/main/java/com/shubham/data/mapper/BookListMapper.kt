package com.shubham.data.mapper

import com.shubham.data.remote.dto.BookDto
import com.shubham.data.remote.dto.BookListDto
import com.shubham.domain.model.Book
import com.shubham.domain.model.BookList

fun BookDto.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        author = authors.firstOrNull()?.name ?: "Unknown",
        summary = summaries.firstOrNull() ?: "",
        coverImageUrl = formats.coverImage,
        previewUrl = formats.html
    )
}

fun BookListDto.toDomain(): BookList {
    return BookList(
        count = count,
        next = next,
        previous = previous,
        books = results.map { it.toDomain() }
    )
}
