package com.shubham.domain.usecase

import com.shubham.domain.common.Logger
import com.shubham.domain.common.Resource
import com.shubham.domain.error.BookShelfError
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
        } catch (e: BookShelfError.NetworkError) {
            emit(Resource.Error(e.message ?: "Network error"))
            logger.e("BookShelf", "Check your internet connection", e)
        } catch (e: BookShelfError.DataNotFound) {
            emit(Resource.Error(e.message ?: "Book not Found"))
            logger.e("BookShelf", "Books not found", e)
        } catch (e: BookShelfError.ServerError) {
            emit(Resource.Error(e.message ?: "Server is unavailable"))
            logger.e("BookShelf", "Server is unavailable", e)
        } catch (e: BookShelfError.UnknownError) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
            logger.e("BookShelf", "Unknown Error", e)
        }
    }.flowOn(Dispatchers.IO)
}