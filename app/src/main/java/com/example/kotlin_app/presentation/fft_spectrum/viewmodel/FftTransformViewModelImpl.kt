package com.example.kotlin_app.presentation.fft_spectrum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_app.util.core.common.resource.RecordResource
import com.example.kotlin_app.domain.repository.MicBufferRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FftTransformViewModelImpl @Inject constructor(private val micBufferRepository: MicBufferRepository) :
   FftTransformViewModel() {
    private val _transformedData = MutableStateFlow<ShortArray>(ShortArray(0))
    override val transformedData: StateFlow<ShortArray> = _transformedData

    init {
        getTransformedData()
    }

    private fun getTransformedData() {
        // todo implement the fft transform in use case
        micBufferRepository.getMicBuffer()
            .filterIsInstance<RecordResource.Success>()
            .map { it.data.copyOf() }
//            .map { data ->
//                withContext(Dispatchers.Default) {
//                    fft(data)
//                }
//            }
            .onEach { _transformedData.value = it }
            .launchIn(viewModelScope)
    }

    override fun startRecording() {
        micBufferRepository.isRecording.value = true
    }
}