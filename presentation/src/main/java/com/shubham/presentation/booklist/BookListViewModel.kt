package com.shubham.presentation.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.domain.common.Resource
import com.shubham.domain.usecase.GetBooksUseCase
import com.shubham.presentation.common.BookListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    private val _bookListState = MutableStateFlow(BookListState())
    val bookListState: StateFlow<BookListState> = _bookListState
    private var currentPage = 1

    init {
        loadBooks()
    }

    private fun loadBooks() {
        getBooksUseCase(currentPage).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _bookListState.update {
                        it.copy(
                            isLoading = true, error = null
                        )
                    }
                }

                is Resource.Success -> {
                    _bookListState.update {
                        it.copy(
                            books = result.data, isLoading = false, error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _bookListState.update {
                        it.copy(
                            error = result.errorMessage, isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun retryLoadBook() {
        loadBooks()
    }
}