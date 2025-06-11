package com.shubham.domain.usecase

import com.shubham.domain.common.Logger
import com.shubham.domain.common.Resource
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetBooksUseCase(
    private val bookRepository: BookRepository,
    private val logger: Logger
) {

    operator fun invoke(page: Int): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading)
        try {
            bookRepository.getBookList(page).collect { books ->
                emit(Resource.Success(books))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
            logger.e("BookShelf", "Failed to load books", e)
        }
    }
}