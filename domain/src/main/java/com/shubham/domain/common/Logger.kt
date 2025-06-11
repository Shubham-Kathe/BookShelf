package com.shubham.domain.common

interface Logger {

    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}