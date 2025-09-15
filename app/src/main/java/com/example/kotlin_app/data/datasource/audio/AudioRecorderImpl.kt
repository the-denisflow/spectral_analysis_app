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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

class AudioRecorderImpl (
    private val logger: Logger,

) : AudioRecorder {
    private var recorder: AudioRecord? = null

     override fun startRecording(activityContext: Context): Flow<RecordResource> {
        if (recorder != null) return flow { RecordResource.Error("Recorder already started")  }

        val channel = AudioFormat.CHANNEL_IN_MONO
        val encoding = AudioFormat.ENCODING_PCM_16BIT
        val minBuf = AudioRecord.getMinBufferSize(sampleRate, channel, encoding)
        if (minBuf <= 0) {
            logger.error( "Invalid min buffer size: $minBuf")
            return flow {  }
        }
        if (ActivityCompat.checkSelfPermission(
                activityContext,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return flow { RecordResource.Error("Permission not granted") }
        }
        recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channel,
            encoding,
            minBuf * 2
        )

        recorder?.startRecording()

      return flow {
          val buf = ShortArray(sampleSize)
          while (coroutineContext.isActive && recorder?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
              val n = recorder?.read(buf, 0, buf.size) ?: 0
              emit(RecordResource.Success(data = buf))
              logger.info("current Amplitude ${buf.last()}")
              delay(uiDelay)
          }
      }.flowOn(Dispatchers.IO)
    }

    companion object {
        // of course the sampleRate + buffer size(shank size) have to be defined first
        const val sampleRate = 44100
        const val sampleSize = 2048
        const val uiDelay = 200L
    }
}