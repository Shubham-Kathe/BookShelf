package com.shubham.bookshelf.navigation

sealed class ScreenRoute(val route: String) {

    object BookList : ScreenRoute("book_list")
    object BookDetails : ScreenRoute("book_details/{bookId}") {
        fun createRoute(bookId: Int) = "book_details/$bookId"
    }
}