package io.nexttutor.android_example.src.features.tutor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import io.nexttutor.android_example.src.shared.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Tutor() {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                EventBus.appLoadedEvent.onNext("")
            }
        }
    }

    fun logout() {
        scope.launch(Dispatchers.IO) {
            firebase.logout()
        }
    }

    Box(modifier = Modifier
        .clickable { logout() }
        .padding(20.dp)
    ) {
        Text(text = "Log out")
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
       Text("Hello world!")
    }
}