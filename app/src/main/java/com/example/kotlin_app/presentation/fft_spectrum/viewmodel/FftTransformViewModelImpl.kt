package com.example.kotlin_app.presentation.fft_spectrum.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.kotlin_app.data.datasource.audio.AudioRecorderImpl
import com.example.kotlin_app.util.core.common.resource.RecordResource
import com.example.kotlin_app.domain.repository.MicBufferRepository
import com.example.kotlin_app.presentation.fft_spectrum.state.FrequencyUiState
import com.example.kotlin_app.util.core.common.computation.computeFFT
import com.example.kotlin_app.util.core.common.computation.findPeakFrequency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FftTransformViewModelImpl @Inject constructor(private val micBufferRepository: MicBufferRepository) :
    FftTransformViewModel() {
    private val _isRecording = MutableStateFlow<Boolean>(false)
    private val _uiState = MutableStateFlow<FrequencyUiState>(FrequencyUiState())
    override val uiState: StateFlow<FrequencyUiState> = _uiState.asStateFlow()

    init {
        getTransformedData()
    }

    private fun getTransformedData() {
        // todo implement the fft transform in use case
        micBufferRepository.getMicBuffer()
            .filterIsInstance<RecordResource.Success>()
            .map { it.data.copyOf() }
            .map { data ->
                withContext(Dispatchers.IO) {
                    computeFFT(data, AudioRecorderImpl.sampleRate.toDouble())
                }
            }
            .onEach {
                _uiState.value =
                    FrequencyUiState(isRecording = true, frequecy = findPeakFrequency(it))
            }
            .launchIn(viewModelScope)
    }

    override fun toggleRecordingState() {
        _isRecording.update {
            !it
        }

        micBufferRepository.isRecording.value = _isRecording.value

        _uiState.update {
            it.copy(isRecording = _isRecording.value)
        }
    }
}