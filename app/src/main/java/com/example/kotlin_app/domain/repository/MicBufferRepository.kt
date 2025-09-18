package com.example.kotlin_app.domain.repository

import com.example.kotlin_app.util.core.common.resource.RecordResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MicBufferRepository {
     fun getMicBuffer(): Flow<RecordResource>
     val isRecording: MutableStateFlow<Boolean>
}