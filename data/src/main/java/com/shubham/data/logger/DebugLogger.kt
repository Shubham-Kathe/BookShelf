package com.shubham.data.logger

import android.util.Log
import com.shubham.domain.common.Logger


class DebugLogger : Logger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}