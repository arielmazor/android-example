package io.nexttutor.android_example

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.nexttutor.android_example.src.shared.EventBus
import io.nexttutor.android_example.src.shared.debugger

class appViewModel : ViewModel() {
    var isSplashVisible by mutableStateOf(true)
        private set

    init {
        EventBus.appLoadedEvent.subscribe {
            isSplashVisible = false
        }
    }
}