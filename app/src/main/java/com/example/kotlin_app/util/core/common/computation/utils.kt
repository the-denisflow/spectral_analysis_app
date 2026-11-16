package com.example.kotlin_app.util.core.common.computation

import com.example.kotlin_app.domain.model.Spectrum
import org.jtransforms.fft.DoubleFFT_1D
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sqrt

fun computeFFT(input: ShortArray, samplingRate: Double): Spectrum {
    val windowedInput = hannWindow(input)

    val n = input.size
    require(n > 1 && n % 128 == 0) { "Input size must be a power of 128" }

    val fft = DoubleFFT_1D(n.toLong())
    fft.realForward(windowedInput)

    val mags = ArrayList<Double>()

    for (k in 0 until n/2) {
        val re = windowedInput[2*k]
        val im = windowedInput[2*k+1]
        val mag = sqrt(re*re + im*im)
        mags.add(mag)
    }

    val frequencies = (0 until  n/2).map { it * samplingRate / n }.toDoubleArray()

    return Spectrum(mags, frequencies)
}

/** Applies a Hann window to reduce spectral leakage. */
fun hannWindow(samples: ShortArray): DoubleArray {
    val n = samples.size
    return DoubleArray(n) { i ->
        val w = 0.5 * (1 - cos(2.0 * PI * i / (n - 1)))
        samples[i] / 32768.0 * w
    }
}

/** Finds out the peak frequency **/
fun findPeakFrequency(spectrum: Spectrum): Double {
    val maxMagnitude = spectrum.magnitudes.max()
    val indexPeakFrequency = spectrum.magnitudes.lastIndexOf(maxMagnitude)
    return spectrum.frequencies[indexPeakFrequency]
}