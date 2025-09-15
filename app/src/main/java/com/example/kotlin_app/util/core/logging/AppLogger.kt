package com.example.kotlin_app.util.core.logging

import android.util.Log
import javax.inject.Inject

class AppLogger @Inject constructor() :Logger  {
    override fun info(message: String) {
        Log.i(TAG, message)
    }

    override fun error(message: String) {
        Log.i(TAG, message)
    }

    companion object {
        const val TAG = "AppLogger"
    }
}