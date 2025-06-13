package com.shubham.data.repository

import com.shubham.data.mapper.toDomain
import com.shubham.data.remote.api.GutendexApi
import com.shubham.data.remote.dto.AuthorsDto
import com.shubham.data.remote.dto.BookDto
import com.shubham.data.remote.dto.BookListDto
import com.shubham.data.remote.dto.FormatsDto
import com.shubham.domain.error.BookShelfError
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.doThrow
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class BookRepositoryImplTest {

    @Mock
    private lateinit var gutendexApi: GutendexApi
    private lateinit var repository: BookRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = BookRepositoryImpl(gutendexApi)
    }

    @Test
    fun `getBookList returns mapped book list`() = runTest {
        val page = 1
        val bookDto = BookDto(
            id = 1,
            title = "Frankenstein",
            authors = listOf(AuthorsDto(name = "Shelley, Mary Wollstonecraft")),
            summaries = listOf("Frankenstein is a novel written in the early 19th century."),
            formats = FormatsDto(
                coverImage = "https://example.com/cover.jpg",
                html = "https://example.com/cover.jpg",
                pdf = null
            )
        )
        val expectedBook = bookDto.toDomain()
        whenever(gutendexApi.getBookList(page)).thenReturn(
            BookListDto(
                1, "", "", results = listOf(bookDto)
            )
        )
        val result = repository.getBookList(page)
        assertEquals(listOf(expectedBook), result)
        verify(gutendexApi).getBookList(page)
    }

    @Test
    fun `getBookList throws DataNotFound when HttpException with 404 occurs`() = runTest {
        val page = 1
        val response = Response.error<Any>(404, ResponseBody.create(null, "Not Found"))
        val httpException = HttpException(response)
        doThrow(httpException).whenever(gutendexApi).getBookList(page)
        val exception = assertFailsWith<BookShelfError.DataNotFound> {
            repository.getBookList(page)
        }
        assertTrue(exception is BookShelfError.DataNotFound)
    }

    @Test
    fun `getBookList throws ServerError when HttpException with 500 occurs`() = runTest {
        val page = 1
        val response = Response.error<Any>(500, ResponseBody.create(null, "Server Error"))
        val httpException = HttpException(response)
        doThrow(httpException).whenever(gutendexApi).getBookList(page)
        val exception = assertFailsWith<BookShelfError.ServerError> {
            repository.getBookList(page)
        }
        assertTrue(exception is BookShelfError.ServerError)
    }

    @Test
    fun `getBookById returns mapped book list`() = runTest {
        val ids = 1
        val bookDto = BookDto(
            id = 1,
            title = "Frankenstein",
            authors = listOf(AuthorsDto(name = "Shelley, Mary Wollstonecraft")),
            summaries = listOf("Frankenstein is a novel written in the early 19th century."),
            formats = FormatsDto(
                coverImage = "https://example.com/cover.jpg",
                html = "https://example.com/cover.jpg",
                pdf = null
            )
        )
        val expectedBook = bookDto.toDomain()
        whenever(gutendexApi.getBookByIds(ids)).thenReturn(
            BookListDto(
                1, "", "", results = listOf(bookDto)
            )
        )
        val result = repository.getBookById(ids)
        assertEquals(listOf(expectedBook), result)
        verify(gutendexApi).getBookByIds(ids)
    }

    @Test
    fun `getBookById throws DataNotFound when HttpException with 404 occurs`() = runTest {
        val bookId = 123
        val response = Response.error<Any>(404, ResponseBody.create(null, "Not Found"))
        val httpException = HttpException(response)
        doThrow(httpException).whenever(gutendexApi).getBookByIds(bookId)
        val exception = assertFailsWith<BookShelfError.DataNotFound> {
            repository.getBookById(bookId)
        }
        assertTrue(exception is BookShelfError.DataNotFound)
    }

    @Test
    fun `getBookById throws ServerError when HttpException with 500 occurs`() = runTest {
        val bookId = 123
        val response = Response.error<Any>(500, ResponseBody.create(null, "Server Error"))
        val httpException = HttpException(response)
        doThrow(httpException).whenever(gutendexApi).getBookByIds(bookId)
        val exception = assertFailsWith<BookShelfError.ServerError> {
            repository.getBookById(bookId)
        }
        assertTrue(exception is BookShelfError.ServerError)
    }
}