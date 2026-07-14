package com.example.trl.ai

import android.util.Log

object Logger {

    private const val TAG = "AIQuizScanner"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(TAG, "===================================")
        Log.e(TAG, message)

        throwable?.let {
            Log.e(TAG, "Exception : ${it::class.java.name}")
            Log.e(TAG, "Message   : ${it.message}")
            Log.e(TAG, "Cause     : ${it.cause}")
            Log.e(TAG, "===================================")
            Log.e(TAG, Log.getStackTraceString(it))
        }
    }
}