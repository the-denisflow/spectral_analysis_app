package com.example.kotlin_app.presentation.fft_spectrum.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AudioRecordView(
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick =  { onClickListener() },
            modifier = Modifier.width(200.dp).height(100.dp)
        ) {
            Text(text = "Start", color = Color.Black)
        }
    }
}