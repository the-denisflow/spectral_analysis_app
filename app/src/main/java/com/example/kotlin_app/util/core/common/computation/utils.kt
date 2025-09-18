package com.example.kotlin_app.util.core.common.computation

import com.example.kotlin_app.domain.model.Spectrum
import org.jtransforms.fft.DoubleFFT_1D
import kotlin.math.sqrt

/**
 * Generates an array of frequencies in Hz corresponding to the FFT bins
 * for a given sampling rate and number of samples.
 *
 * @param rate The sampling rate in Hz.
 * @param numberOfSamples The number of samples in the signal.
 * @return A [DoubleArray] containing the frequency values from 0 up to half the sampling rate (Nyquist frequency).
 *
 */
fun computeFFT(input: DoubleArray, samplingRate: Double): Spectrum {
    val n = input.size
    require(n > 1 && n % 128 == 0) { "Input size must be a power of 128" }

    val fft = DoubleFFT_1D(n.toLong())
    fft.realForward(input)

    val mags = ArrayList<Double>()

    for (k in 0 until n/2) {
        val re = input[2*k]
        val im = input[2*k+1]
        val mag = sqrt(re*re + im*im)
        mags.add(mag)
    }

    val frequencies = (0 until  n/2).map { it * samplingRate / n }.toDoubleArray()

    return Spectrum(mags, frequencies)
}