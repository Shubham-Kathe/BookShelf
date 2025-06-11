package com.shubham.data.mapper

import com.shubham.data.remote.dto.AuthorsDto
import com.shubham.data.remote.dto.BookDto
import com.shubham.data.remote.dto.BookListDto
import com.shubham.data.remote.dto.FormatsDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlin.test.Test

class MapperTest {

    @Test
    fun `BookDto toDomain maps all fields correctly`() {
        val dto = BookDto(
            id = 1,
            title = "Book Title",
            authors = listOf(AuthorsDto("Author Shree")),
            summaries = listOf("Book summary"),
            formats = FormatsDto(
                coverImage = "https://example.com/image.jpg",
                html = "https://example.com/html",
                pdf = null
            )
        )
        val domain = dto.toDomain()
        assertEquals(1, domain.id)
        assertEquals("Book Title", domain.title)
        assertEquals("Author Shree", domain.author)
        assertEquals("Book summary", domain.summary)
        assertEquals("https://example.com/image.jpg", domain.coverImageUrl)
        assertEquals("https://example.com/html", domain.previewUrl)
    }

    @Test
    fun `BookDto toDomain handles null or empty authors and summaries`() {
        val dto = BookDto(
            id = 2,
            title = "No Author",
            authors = emptyList(),
            summaries = emptyList(),
            formats = FormatsDto(
                coverImage = "https://example.com/img.jpg",
                html = null,
                pdf = "https://example.com/pdf"
            )
        )
        val domain = dto.toDomain()
        assertEquals("Unknown", domain.author)
        assertEquals("", domain.summary)
        assertEquals(null, domain.previewUrl)
    }

    @Test
    fun `BookListDto toDomain maps list of BookDto correctly`() {
        val dto = BookListDto(
            count = 2, next = "page2", previous = null, results = listOf(
                BookDto(
                    id = 1,
                    title = "Book One",
                    authors = listOf(AuthorsDto("Author A")),
                    summaries = listOf("Summary A"),
                    formats = FormatsDto(
                        coverImage = "url1", html = "html1", pdf = null
                    )
                ), BookDto(
                    id = 2,
                    title = "Book Two",
                    authors = listOf(AuthorsDto("Author B")),
                    summaries = listOf("Summary B"),
                    formats = FormatsDto(
                        coverImage = "url2", html = "html2", pdf = null
                    )
                )
            )
        )
        val domain = dto.toDomain()
        assertEquals(2, domain.count)
        assertEquals("page2", domain.next)
        assertNull(domain.previous)
        assertEquals(2, domain.books.size)
        assertEquals("Book One", domain.books[0].title)
        assertEquals("Book Two", domain.books[1].title)
    }
}