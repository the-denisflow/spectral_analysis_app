package com.example.kotlin_app.data.repository

import android.content.Context
import com.example.kotlin_app.util.core.common.resource.RecordResource
import com.example.kotlin_app.data.datasource.audio.AudioRecorder
import com.example.kotlin_app.domain.repository.MicBufferRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MicBufferRepositoryImpl @Inject constructor(private val audioRecorder: AudioRecorder, private val applicationContext: Context) :
    MicBufferRepository {
    override fun getMicBuffer(): Flow<RecordResource> = audioRecorder.startRecording(applicationContext)
}