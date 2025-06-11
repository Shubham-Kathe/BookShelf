package com.shubham.data.repository

import com.shubham.data.mapper.toDomain
import com.shubham.data.remote.api.GutendexApi
import com.shubham.data.remote.dto.AuthorsDto
import com.shubham.data.remote.dto.BookDto
import com.shubham.data.remote.dto.BookListDto
import com.shubham.data.remote.dto.FormatsDto
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertFailsWith

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
    fun `getBookList throws exception when API fails`() = runTest {
        val page = 1
        val exception = RuntimeException("API Error")
        whenever(gutendexApi.getBookList(page)).thenThrow(exception)
        assertFailsWith<RuntimeException> {
            repository.getBookList(page)
        }
        verify(gutendexApi).getBookList(page)
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
    fun `getBookById throws exception when API fails`() = runTest {
        val ids = 1
        val exception = RuntimeException("API Error")
        whenever(gutendexApi.getBookByIds(ids)).thenThrow(exception)
        assertFailsWith<RuntimeException> {
            repository.getBookById(ids)
        }
        verify(gutendexApi).getBookByIds(ids)
    }
}