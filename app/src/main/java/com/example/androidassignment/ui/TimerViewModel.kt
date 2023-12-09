package com.example.androidassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.models.TimerState
import com.example.androidassignment.models.TimerStates
import com.example.androidassignment.utils.extensions.formatToTwoDigit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class TimerViewModel : ViewModel() {

    private var _timerLeft = TimerActivity.TIMER_DURATION

    private val _timerState = MutableStateFlow(TimerState("01:00:00", 0f, state = TimerStates.IDLE))
    val timerState: StateFlow<TimerState> = _timerState

    private val _isTicking = MutableStateFlow(false)
    val isTicking: StateFlow<Boolean> = _isTicking

    fun startTimer() {
        _isTicking.value = true
        updateTimerState(isTicking = true)
    }

    fun pauseTimer() {
        _isTicking.value = false
        updateTimerState(isTicking = false)
    }

    fun stopTimer() {

        updateTimerState(isTicking = false)

        // Reset to initial values
        resetTimer()
        _timerState.value = TimerState("01:00:00", 0f, state = TimerStates.STOPPED)
    }

    private fun resetTimer(){
        _timerLeft = TimerActivity.TIMER_DURATION
        _isTicking.value = false
    }

    private lateinit var countDownJob: Job

    private suspend fun countDownTimer(
        duration: Long,
        interval: Long = TimerActivity.COUNTDOWN_INTERVAL,
        block: suspend (Long, Long, Long, Long, Float) -> Unit
    ) {
        var remainingTime = duration
        withContext(Dispatchers.IO) {
            countDownJob = launch {
                while (remainingTime > 0) {
                    val minutes = remainingTime / 60_000L
                    val seconds = (remainingTime / 1_000L) % 60
                    val milliseconds = remainingTime % 1_000L
                    val progress =
                        100 - (remainingTime.toFloat() / TimerActivity.TIMER_DURATION) * 100

                    delay(interval)
                    remainingTime -= interval

                    block(minutes, seconds, milliseconds, remainingTime, round(progress))
                }
            }

            countDownJob.start()
        }
    }

    private fun updateTimerState(isTicking: Boolean) {
        viewModelScope.launch {
            if (isTicking) {
                countDownTimer(
                    _timerLeft,
                    block = { minute, seconds, milliseconds, remainingTime, progress ->
                        _timerLeft = remainingTime
                        if (seconds == 0L && milliseconds <= TimerActivity.COUNTDOWN_INTERVAL) {
                            _timerState.value = TimerState("01:00:00", 0f, state = TimerStates.COMPLETED)
                            resetTimer()
                        } else {
                            _timerState.value = TimerState("${minute.formatToTwoDigit()}:${seconds.formatToTwoDigit()}:${milliseconds.formatToTwoDigit()}", progress, state = TimerStates.TICKING)
                        }
                    })
            } else {
                countDownJob.cancel()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        updateTimerState(false)
        resetTimer()
    }

}