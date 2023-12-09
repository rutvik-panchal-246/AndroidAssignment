package com.example.androidassignment.models

data class TimerState(val timerLabel: String, val progress: Float, val state: TimerStates)

enum class TimerStates {
    IDLE, TICKING, PAUSED, STOPPED ,COMPLETED
}
