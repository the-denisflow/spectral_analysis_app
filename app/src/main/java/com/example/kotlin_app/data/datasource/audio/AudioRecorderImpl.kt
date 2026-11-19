package com.example.kotlin_app.data.datasource.audio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.example.kotlin_app.util.core.common.resource.RecordResource
import com.example.kotlin_app.util.core.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive

class AudioRecorderImpl(
    private val logger: Logger,
) : AudioRecorder {
    private var recorder: AudioRecord? = null

    override fun startRecording(activityContext: Context): Flow<RecordResource> = flow {
        if (recorder != null) {
            emit(RecordResource.Error("Recorder already started"))
        }

        if (ActivityCompat.checkSelfPermission(
                activityContext,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            emit(RecordResource.Error("Permission not granted"))
            return@flow
        }

        val channel = AudioFormat.CHANNEL_IN_MONO
        val encoding = AudioFormat.ENCODING_PCM_16BIT
        val minBuf = AudioRecord.getMinBufferSize(sampleRate, channel, encoding)

        if (minBuf <= 0) {
            logger.error("Invalid min buffer size: $minBuf")
            emit(RecordResource.Error("Microphone not available"))
            return@flow
        }

        recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channel,
            encoding,
            minBuf * 2
        )

        try {
            recorder?.startRecording()
            val buf = ShortArray(sampleSize)

            while (currentCoroutineContext().isActive && recorder?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                val read = recorder?.read(buf, 0, buf.size) ?: 0
                when {
                    read < 0 -> {
                        emit(RecordResource.Error("Microphone read failed ($read)"))
                        break
                    }
                    read == buf.size -> emit(RecordResource.Success(data = buf))
                }
            }

        } finally {
            stopRecording()
        }
    }.flowOn(Dispatchers.IO)

    override fun stopRecording() {
        recorder?.stop()
        recorder?.release()
        recorder = null
    }

    companion object {
        const val sampleRate = 44100
        const val sampleSize = 2048
    }
}