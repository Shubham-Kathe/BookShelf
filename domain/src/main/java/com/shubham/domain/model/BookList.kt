package com.shubham.domain.model

data class BookList (
	val count: Int,
	val next: String?,
	val previous: String?,
	val books: List<Book>
)