package io.nexttutor.android_example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.nexttutor.android_example.src.features.auth.Login
import io.nexttutor.android_example.src.features.auth.Signup
import io.nexttutor.android_example.src.features.tutor.Tutor
import io.nexttutor.android_example.src.shared.EventBus.appLoadedEvent
import io.nexttutor.android_example.src.shared.firebase

@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = if (firebase.fbUser != null) "tutor" else "login"
    ) {
        composable("login") { Login(navController) }
        composable("signup") { Signup(navController) }
        composable("tutor") { Tutor() }
    }
}