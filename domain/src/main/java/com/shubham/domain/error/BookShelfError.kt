package com.shubham.domain.error

sealed class BookShelfError : Exception() {

    object NetworkError : BookShelfError()
    object DataNotFound : BookShelfError()
    object ServerError : BookShelfError()
    data class UnknownError(val errorMessage: String?) : BookShelfError()
}