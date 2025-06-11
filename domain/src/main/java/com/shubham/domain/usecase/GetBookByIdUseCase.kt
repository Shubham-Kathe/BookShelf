package com.shubham.domain.usecase

import com.shubham.domain.common.Logger
import com.shubham.domain.common.Resource
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetBookByIdUseCase(
    private val bookRepository: BookRepository, private val logger: Logger
) {

    operator fun invoke(ids: Int): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(bookRepository.getBookById(ids)))
        } catch (e: Exception) {
            logger.e("BookShelf", "Failed to load books", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}