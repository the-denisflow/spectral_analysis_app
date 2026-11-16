package com.example.kotlin_app.presentation.fft_spectrum.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_app.presentation.fft_spectrum.state.FrequencyUiState

@Composable
fun AudioRecordView(
    frequencyState: FrequencyUiState,
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.QUENCY_TEXT_MARGIN),
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = if (frequencyState.frequecy != null) "${frequencyState.frequecy}" else "__",
                fontSize = Dimens.FREQUENCY_TEXT_FONT_SIZE
            )
            Text(
                text = " Hz",
                fontSize = Dimens.FREQUENCY_TEXT_FONT_SIZE
            )
        }

        Button(
            onClick = { onClickListener() },
            modifier = Modifier
                .width(Dimens.STARTBUTTON_WIDTH)
                .height(Dimens.STARTBUTTON_HEIGHT)
        ) {
            Text(text = if (!frequencyState.isRecording) "Start" else "Pause", color = Color.Black, fontSize = Dimens.STARTBUTTON_FONT_SIZE)
        }
    }
}

@Preview
@Composable
fun AudioRecordViewPreview() {
    val mockFrequency = FrequencyUiState(true, 55.0)
    AudioRecordView(
        mockFrequency,
        Modifier.background(Color.White)
    )
}