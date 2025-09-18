package com.example.kotlin_app.presentation.fft_spectrum.common

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlin_app.R
import com.example.kotlin_app.util.core.logging.Logger
import com.example.kotlin_app.util.core.permissions.AudioPermissionRequester
import com.example.kotlin_app.util.core.permissions.AudioPermissionRequesterImpl
import com.example.kotlin_app.data.datasource.audio.AudioRecorder
import com.example.kotlin_app.data.datasource.audio.AudioRecorderImpl
import com.example.kotlin_app.presentation.fft_spectrum.viewmodel.FftTransformViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BaseFragment : Fragment() {
    @Inject lateinit var logger: Logger
    private lateinit var audioPermissionRequester: AudioPermissionRequester
    private val fftTransformViewModel: FftTransformViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioPermissionRequester = AudioPermissionRequesterImpl(this)
        audioPermissionRequester.requestAudioPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                fftTransformViewModel.transformedData.collectLatest { arr ->
                    logger.info("micData: ${arr.contentToString()}")
                }
        }
        }
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AudioPermissionRequesterImpl.REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                logger.info("Audio permission granted")
            } else {
                logger.info("Audio permission denied")
            }
        }
    }

}