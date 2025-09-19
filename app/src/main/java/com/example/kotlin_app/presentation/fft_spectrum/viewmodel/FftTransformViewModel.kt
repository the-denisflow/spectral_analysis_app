package com.example.kotlin_app.presentation.fft_spectrum.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class FftTransformViewModel: ViewModel() {
    abstract val transformedData: StateFlow<ShortArray>
    abstract fun startRecording()
}