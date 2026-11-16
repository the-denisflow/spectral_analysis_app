package com.example.kotlin_app.presentation.fft_spectrum.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlin_app.presentation.fft_spectrum.state.FrequencyUiState
import kotlinx.coroutines.flow.StateFlow

abstract class FftTransformViewModel: ViewModel() {
    abstract val uiState: StateFlow<FrequencyUiState>
    abstract fun toggleRecordingState()
}