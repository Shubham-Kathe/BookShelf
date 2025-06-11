package com.shubham.data.repository

import com.shubham.data.mapper.toDomain
import com.shubham.data.remote.api.GutendexApi
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookRepositoryImpl(private val gutendexApi: GutendexApi) : BookRepository {

    override fun getBookList(page: Int): Flow<List<Book>> = flow {
        val books = gutendexApi.getBookList(page).results.map { it.toDomain() }
        emit(books)
    }.flowOn(Dispatchers.IO)

    override fun getBookById(ids: Int): Flow<List<Book>> = flow {
        val books = gutendexApi.getBookByIds(ids).results.map { it.toDomain() }
        emit(books)
    }.flowOn(Dispatchers.IO)
}

