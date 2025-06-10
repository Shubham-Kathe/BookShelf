package com.shubham.domain.repository
import com.shubham.domain.model.Book
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    fun getBookList(page: Int): Flow<List<Book>>
    fun getBookById(ids: Int): Flow<List<Book>>
}