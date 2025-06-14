package com.shubham.presentation.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.domain.common.Logger
import com.shubham.domain.common.Resource
import com.shubham.domain.usecase.GetBookByIdUseCase
import com.shubham.presentation.common.BookListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBooksByIdUseCase: GetBookByIdUseCase,
    private val logger: Logger
) : ViewModel() {

    private val _bookListState = MutableStateFlow(BookListState())
    val bookListState: StateFlow<BookListState> = _bookListState

    init {
        loadBookDetails()
    }

    private fun loadBookDetails() {
        val bookId = savedStateHandle.get<Int>("bookId") ?: return
        getBooksByIdUseCase(bookId).onEach { result ->
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

    fun logger(message: String, e: Throwable) {
        logger.e("BookShelf", message, e)
    }

    fun retryLoadBook() {
        loadBookDetails()
    }
}