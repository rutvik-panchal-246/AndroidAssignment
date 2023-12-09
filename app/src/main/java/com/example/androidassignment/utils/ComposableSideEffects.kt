package com.example.androidassignment.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

object ComposableSideEffects {

    @Composable
    fun RegisterLifecycleObserver() {
        val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        AppSession.isAppInBackground = false
                    }

                    Lifecycle.Event.ON_START -> {
                        AppSession.isAppInBackground = false
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        AppSession.isAppInBackground = false
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        AppSession.isAppInBackground = false
                    }

                    Lifecycle.Event.ON_STOP -> {
                        AppSession.isAppInBackground = true
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                        AppSession.isAppInBackground = true
                    }

                    else -> {}
                }
            }

            // Add the observer to the lifecycle
            lifecycleOwner.lifecycle.addObserver(observer)

            // When the effect leaves the Composition, remove the observer
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)

            }
        }
    }

}