package com.example.kotlin_app.util.core.common.resource

sealed class RecordResource(data: ShortArray? = null, message: String? = null) {
    class Success(val data: ShortArray) : RecordResource(data = data)
    class Paused(val message: String) : RecordResource(message = message)
    class Error(val message: String) : RecordResource(message = message)
}
