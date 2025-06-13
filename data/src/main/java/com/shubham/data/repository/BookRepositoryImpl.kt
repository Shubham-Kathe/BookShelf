package com.shubham.data.repository

import com.shubham.data.mapper.toDomain
import com.shubham.data.remote.api.GutendexApi
import com.shubham.domain.error.BookShelfError
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository
import retrofit2.HttpException
import java.io.IOException

class BookRepositoryImpl(private val gutendexApi: GutendexApi) : BookRepository {

    override suspend fun getBookList(page: Int): List<Book> {
        return try {
            gutendexApi.getBookList(page).results.map { it.toDomain() }
        } catch (e: IOException) {
            throw BookShelfError.NetworkError
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> throw BookShelfError.DataNotFound
                in 500..599 -> throw BookShelfError.ServerError
                else -> throw BookShelfError.UnknownError(e.message())
            }
        } catch (e: Exception) {
            throw BookShelfError.UnknownError(e.message)
        }
    }

    override suspend fun getBookById(ids: Int): List<Book> {
        return try {
            gutendexApi.getBookByIds(ids).results.map { it.toDomain() }
        } catch (e: IOException) {
            throw BookShelfError.NetworkError
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> throw BookShelfError.DataNotFound
                in 500..599 -> throw BookShelfError.ServerError
                else -> throw BookShelfError.UnknownError(e.message())
            }
        } catch (e: Exception) {
            throw BookShelfError.UnknownError(e.message)
        }
    }
}

