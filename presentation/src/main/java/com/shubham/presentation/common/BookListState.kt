package com.shubham.presentation.common

import com.shubham.domain.model.Book


data class BookListState(
    val isLoading: Boolean = false, val books: List<Book> = emptyList(), val error: String? = null
)