package com.weighttracker.screen.converter

sealed interface ConverterEvent {
    data class LbInput(val lb: Double) : ConverterEvent
    data class KgInput(val kg: Double) : ConverterEvent
    data class FeetInput(val feet: Double) : ConverterEvent
    data class MInput(val m: Double) : ConverterEvent
    object Reset : ConverterEvent
}