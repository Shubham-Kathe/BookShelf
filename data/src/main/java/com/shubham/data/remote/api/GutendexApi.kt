package com.shubham.data.remote.api

import com.shubham.data.remote.dto.BookListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GutendexApi {

    @GET("books/")
    suspend fun getBookList(
        @Query("page") page: Int
    ): BookListDto

    @GET("books/")
    suspend fun getBookByIds(
        @Query("ids") ids: Int,
    ): BookListDto

}