package com.shubham.domain.usecase

import app.cash.turbine.test
import com.shubham.domain.common.Logger
import com.shubham.domain.common.Resource
import com.shubham.domain.model.Book
import com.shubham.domain.repository.BookRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

@OptIn(ExperimentalCoroutinesApi::class)
class BookUseCaseTest {

    private lateinit var bookRepository: BookRepository
    private lateinit var logger: Logger
    private lateinit var getBooksUseCase: GetBooksUseCase

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
        getBooksUseCase = GetBooksUseCase(bookRepository, logger)
    }

    @Test
    fun `invoke emits loading and success when repository returns books`() = runTest {

        // Arrange
        `when`(bookRepository.getBookList(1)).thenReturn(
            flow { emit(listOf(fakeBook)) })

        // Act & Assert
        getBooksUseCase(1).test {
            assert(awaitItem() is Resource.Loading)
            val success = awaitItem()
            assert(success is Resource.Success)
            assert((success as Resource.Success).data == listOf(fakeBook))
            awaitComplete()
        }

        verify(bookRepository).getBookList(1)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun `when repository returns empty list, emit Success with empty data`() = runTest {

        // Arrange
        `when`(bookRepository.getBookList(1)).thenReturn(
            flow { emit(emptyList()) })

        // Act & Assert
        getBooksUseCase(1).test {
            assert(awaitItem() is Resource.Loading)

            val success = awaitItem()
            assert(success is Resource.Success)
            assert((success as Resource.Success).data.isEmpty())

            awaitComplete()
        }

        verify(bookRepository).getBookList(1)
        verifyNoMoreInteractions(logger)
    }

    @Test
    fun `invoke emits loading and error when repository throws exception`() = runTest {
        // Arrange
        val errorMessage = "Network failure"
        `when`(bookRepository.getBookList(any())).thenReturn(
            flow { throw RuntimeException(errorMessage) })

        // Act & Assert
        getBooksUseCase(1).test {
            assert(awaitItem() is Resource.Loading)
            val error = awaitItem()
            assert(error is Resource.Error)
            assert((error as Resource.Error).errorMessage == errorMessage)
            awaitComplete()
        }

        verify(bookRepository).getBookList(1)
        verify(logger).e(eq("BookShelf"), eq("Failed to load books"), any())
    }
}