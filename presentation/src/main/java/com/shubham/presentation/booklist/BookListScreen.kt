package com.shubham.presentation.booklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shubham.presentation.common.components.LoadingIndicator
import com.shubham.presentation.common.components.RetryView

@Composable
fun BookListScreen(
    viewModel: BookListViewModel = hiltViewModel(), onBookClick: (Int) -> Unit
) {
    val bookListState by viewModel.bookListState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "BookShelf",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 32.dp)
        )
        if (bookListState.isLoading) {
            LoadingIndicator()
        } else if (bookListState.error != null) {
            RetryView {
                viewModel.retryLoadBook()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(bookListState.books) { book ->
                    BookCard(book, onBookClick)
                }
            }
        }
    }
}





