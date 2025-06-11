package com.shubham.domain.repository

import com.shubham.domain.model.Book

interface BookRepository {

    suspend fun getBookList(page: Int): List<Book>
    suspend fun getBookById(ids: Int): List<Book>
}