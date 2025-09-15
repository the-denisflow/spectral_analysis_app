package com.example.kotlin_app.util.core.common.computation

fun frequencyRange(rate : Double, numberOfSamples : Int): DoubleArray {
    var frequencies = DoubleArray(0)
    var count = 0.0
    val step = rate/numberOfSamples
    for(i in 0 until  numberOfSamples/2){
        frequencies += count
        count += step
    }
    return frequencies
}