package com.shubham.presentation;

import com.shubham.domain.common.Resource
import com.shubham.domain.model.Book
import com.shubham.domain.usecase.GetBooksUseCase;
import com.shubham.presentation.booklist.BookListViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class BookListViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var getBooksUseCase: GetBooksUseCase
    private lateinit var viewModel: BookListViewModel

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
        getBooksUseCase = mock()
    }

    @Test
    fun `loadBooks emits Loading then Success`() = runTest {
        val books = listOf(fakeBook)
        val flow = flow {
            emit(Resource.Loading)
            emit(Resource.Success(books))
        }

        whenever(getBooksUseCase.invoke(1)).thenReturn(flow)

        viewModel = BookListViewModel(getBooksUseCase)

        val state = viewModel.bookListState.first { !it.isLoading }

        assertEquals(books, state.books)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `loadBooks emits Loading then Error`() = runTest {
        val errorMessage = "Network error"
        val flow = flow {
            emit(Resource.Loading)
            emit(Resource.Error(errorMessage))
        }

        whenever(getBooksUseCase.invoke(1)).thenReturn(flow)

        viewModel = BookListViewModel(getBooksUseCase)

        val state = viewModel.bookListState.first { !it.isLoading }

        assertEquals(errorMessage, state.error)
        assertTrue(state.books.isNullOrEmpty())
        assertFalse(state.isLoading)
    }

}
