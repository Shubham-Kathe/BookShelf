package com.shubham.bookshelf.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shubham.presentation.bookdetails.BookDetailsScreen
import com.shubham.presentation.booklist.BookListScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.BookList.route) {

        composable(route = ScreenRoute.BookList.route) {
            BookListScreen(
                onBookClick = { bookId ->
                    navController.navigate(ScreenRoute.BookDetails.createRoute(bookId))
                })
        }

        composable(
            route = ScreenRoute.BookDetails.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) {
            BookDetailsScreen(
                onCloseClick = {
                    navController.popBackStack()
                })
        }
    }
}