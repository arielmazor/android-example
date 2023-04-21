package io.nexttutor.android_example

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import io.nexttutor.android_example.src.features.`splash-screen`.SplashScreen

@Composable
fun App() {
    val appViewModel = viewModel<appViewModel>()

    Navigation()
    if (appViewModel.isSplashVisible) {
        SplashScreen()
    }
}