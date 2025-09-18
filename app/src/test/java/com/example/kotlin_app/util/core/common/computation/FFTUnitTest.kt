package com.example.kotlin_app.util.core.common.computation

import org.junit.Test
import org.junit.Assert.*
import kotlin.math.PI
import kotlin.math.sin

class FFTUnitTest {
    @Test
    fun computeFFT_returnsExpectedResult() {
        val n = 1024
        val fs = 1024.0
        val f = 50.0

        val input = DoubleArray(n) { i ->
            sin(2.0 * PI * f * i / fs)
        }

        val fftResult = computeFFT(input, fs)

        val mags = fftResult.magnitudes
        val freq = fftResult.frequencies

        val maxMag = mags.let {
            it.indexOf(it.max())
        }
        val maxFreq = freq[maxMag]

        assertEquals(f, maxFreq, 0.0)
    }
}