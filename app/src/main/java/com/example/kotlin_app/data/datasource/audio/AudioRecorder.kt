package com.example.kotlin_app.data.datasource.audio

import android.content.Context
import com.example.kotlin_app.util.core.common.resource.RecordResource
import kotlinx.coroutines.flow.Flow

interface AudioRecorder {
    fun startRecording(activityContext: Context): Flow<RecordResource>
}