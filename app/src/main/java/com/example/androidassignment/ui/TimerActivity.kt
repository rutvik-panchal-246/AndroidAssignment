package com.example.androidassignment.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.androidassignment.services.NotificationService
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TimerActivity : ComponentActivity() {

    // Register the permissions callback
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted
            } else {
                // Explain to the user why we required permissions and re-initiate for the permissions
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askRequiredPermissions()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val viewModel = getViewModel<TimerViewModel>()

                val timerState = viewModel.timerState.collectAsState()
                val isTicking = viewModel.isTicking.collectAsState()

                TimerScreen(
                    timerStates = timerState.value,
                    isTicking = isTicking.value,
                    startPauseAction = {
                        if (isTicking.value) {
                            viewModel.pauseTimer()
                        } else {
                            viewModel.startTimer()
                        }
                    }, stopAction = {
                        viewModel.stopTimer()
                    })
            }
        }
    }

    private fun askRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Permission is already granted
            }
        }
    }

    companion object {

        // Timer duration is an milliseconds
        const val TIMER_DURATION: Long = 10000
        const val COUNTDOWN_INTERVAL: Long = 20
    }
}

