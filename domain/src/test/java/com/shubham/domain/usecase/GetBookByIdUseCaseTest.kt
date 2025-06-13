package com.shubham.domain.usecase

import app.cash.turbine.test
import com.shubham.domain.common.Logger
import com.shubham.domain.common.Resource
import com.shubham.domain.error.BookShelfError
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

@OptIn(ExperimentalCoroutinesApi::class)
class GetBookByIdUseCaseTest {

    private lateinit var bookRepository: BookRepository
    private lateinit var logger: Logger
    private lateinit var getBookByIdUseCase: GetBookByIdUseCase
    private val fakeBook = Book(
        id = 1,
        title = "Frankenstein",
        author = "Shelley, Mary Wollstonecraft",
        summary = "Frankenstein is a novel written in the early 19th century.",
        coverImageUrl = "https://example.com/cover.jpg",
        previewUrl = "https://example.com/preview"
    )

    @Before
    fun setup() {
        bookRepository = mock()
        logger = mock()
        getBookByIdUseCase = GetBookByIdUseCase(bookRepository, logger)
    }

    @Test
    fun `invoke emits loading and success when repository returns books`() = runTest {
        `when`(bookRepository.getBookById(1)).thenReturn(listOf(fakeBook))
        getBookByIdUseCase(1).test {
            assert(awaitItem() is Resource.Loading)
            val success = awaitItem()
            assert(success is Resource.Success)
            assert((success as Resource.Success).data == listOf(fakeBook))
            awaitComplete()
        }
        verify(bookRepository).getBookById(1)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun `when repository returns empty list, emit Success with empty data`() = runTest {
        `when`(bookRepository.getBookById(1)).thenReturn(emptyList())
        getBookByIdUseCase(1).test {
            assert(awaitItem() is Resource.Loading)
            val success = awaitItem()
            assert(success is Resource.Success)
            assert((success as Resource.Success).data.isEmpty())

            awaitComplete()
        }
        verify(bookRepository).getBookById(1)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun `invoke emits loading and error when repository throws NetworkError`() = runTest {
        doAnswer { throw BookShelfError.NetworkError }.`when`(bookRepository).getBookById(any())
        getBookByIdUseCase(1).test {
            assert(awaitItem() is Resource.Loading)
            val error = awaitItem()
            assert(error is Resource.Error)
            assertEquals("Network error", (error as Resource.Error).errorMessage)
            awaitComplete()
        }
        verify(bookRepository).getBookById(1)
        verify(logger).e(eq("BookShelf"), eq("Check your internet connection"), any())
    }
}