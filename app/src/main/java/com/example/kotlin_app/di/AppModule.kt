package com.example.kotlin_app.di

import android.content.Context
import com.example.kotlin_app.data.datasource.audio.AudioRecorder
import com.example.kotlin_app.data.datasource.audio.AudioRecorderImpl
import com.example.kotlin_app.data.repository.MicBufferRepositoryImpl
import com.example.kotlin_app.domain.repository.MicBufferRepository
import com.example.kotlin_app.util.core.logging.AppLogger
import com.example.kotlin_app.util.core.logging.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

    @Provides
    @Singleton
    fun provideAudioRecorder(appLogger: Logger): AudioRecorder = AudioRecorderImpl(appLogger)

    @Provides
    @Singleton
    fun provideMicBufferRepository(
        audioRecorder: AudioRecorder,
        @ApplicationContext applicationContext: Context
    ): MicBufferRepository = MicBufferRepositoryImpl(audioRecorder, applicationContext)
}