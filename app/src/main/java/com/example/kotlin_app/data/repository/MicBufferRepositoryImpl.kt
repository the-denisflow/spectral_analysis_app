package com.example.kotlin_app.data.repository

import android.content.Context
import com.example.kotlin_app.util.core.common.resource.RecordResource
import com.example.kotlin_app.data.datasource.audio.AudioRecorder
import com.example.kotlin_app.domain.repository.MicBufferRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MicBufferRepositoryImpl @Inject constructor(
    private val audioRecorder: AudioRecorder,
    private val applicationContext: Context
) : MicBufferRepository {
    private val _isRecording = MutableStateFlow(false)
    override val isRecording: MutableStateFlow<Boolean> = _isRecording

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMicBuffer(): Flow<RecordResource> =
        isRecording
            .flatMapLatest { isRecording ->
                if (isRecording) {
                    audioRecorder.startRecording(applicationContext)
                } else {
                    audioRecorder.stopRecording()
                    flow {
                        emit(RecordResource.Paused(message = "Recording paused"))
                    }
                }
            }
}