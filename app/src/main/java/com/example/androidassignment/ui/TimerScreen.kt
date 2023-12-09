package com.example.androidassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.androidassignment.R
import com.example.androidassignment.models.TimerState
import com.example.androidassignment.models.TimerStates
import com.example.androidassignment.services.NotificationService
import com.example.androidassignment.ui.theme.Blue
import com.example.androidassignment.ui.theme.Green
import com.example.androidassignment.ui.theme.Orange
import com.example.androidassignment.ui.theme.White
import com.example.androidassignment.ui.theme.padding42
import com.example.androidassignment.ui.theme.size14
import com.example.androidassignment.ui.theme.size180
import com.example.androidassignment.ui.views.CountDownIndicator
import com.example.androidassignment.utils.AppSession
import com.example.androidassignment.utils.ComposableSideEffects

@Composable
fun TimerScreen(
    timerStates: TimerState,
    isTicking: Boolean,
    startPauseAction: () -> Unit,
    stopAction: () -> Unit
) {

    // Register composable with activity lifecycle events
    ComposableSideEffects.RegisterLifecycleObserver()

    // Show local notification when app is an background and time is completed
    when (timerStates.state) {
        TimerStates.COMPLETED -> {
            if (AppSession.isAppInBackground) {
                NotificationService.showNotification(LocalContext.current)
            }
        }

        else -> {}
    }

    // User interface for the timer screen
    Column(
        modifier = Modifier.fillMaxSize().background(color = Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.lbl_timer_counter),
            color = Green,
            fontSize = TextUnit(
                28f,
                TextUnitType.Sp
            ),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(padding42))
        CountDownIndicator(
            progress = timerStates.progress,
            time = timerStates.timerLabel,
            size = size180,
            stroke = size14
        )
        Spacer(modifier = Modifier.height(padding42))
        TimerActions(
            isTicking = isTicking,
            startPauseAction = startPauseAction,
            stopAction = stopAction
        )
    }
}

@Composable
fun TimerActions(
    modifier: Modifier = Modifier,
    isTicking: Boolean,
    startPauseAction: () -> Unit,
    stopAction: () -> Unit
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(
            onClick = { startPauseAction() },
        ) {
            Text(
                text = if (isTicking.not()) stringResource(id = R.string.btn_timer_start) else stringResource(
                    id = R.string.btn_timer_pause
                )
            )
        }
        FloatingActionButton(
            onClick = {
                stopAction()
            },
            containerColor = Orange,
            contentColor = White,
        ) {
            Text(text = stringResource(id = R.string.btn_timer_stop))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TimerScreen(
        TimerState("00:30:00", 3f, state = TimerStates.IDLE),
        true,
        startPauseAction = {},
        stopAction = {})
}