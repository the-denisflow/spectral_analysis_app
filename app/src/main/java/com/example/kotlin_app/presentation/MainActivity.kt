package com.example.kotlin_app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_app.presentation.fft_spectrum.fragment.AudioRecordFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, AudioRecordFragment())
            .commit()
    }
}