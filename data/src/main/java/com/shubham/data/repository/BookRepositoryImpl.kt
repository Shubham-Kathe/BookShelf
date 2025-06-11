package com.shubham.data.repository

import com.shubham.data.mapper.toDomain
import com.shubham.data.remote.api.GutendexApi
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository

class BookRepositoryImpl(private val gutendexApi: GutendexApi) : BookRepository {

    override suspend fun getBookList(page: Int): List<Book> =
        gutendexApi.getBookList(page).results.map { it.toDomain() }

    override suspend fun getBookById(ids: Int): List<Book> =
        gutendexApi.getBookByIds(ids).results.map { it.toDomain() }
}

