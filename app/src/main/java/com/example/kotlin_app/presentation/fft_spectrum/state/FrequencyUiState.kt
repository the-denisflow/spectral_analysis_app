package com.example.kotlin_app.presentation.fft_spectrum.state

data class FrequencyUiState(
    val isRecording: Boolean = false,
    val frequecy: Double? = null,
    val error: String? = null
)
