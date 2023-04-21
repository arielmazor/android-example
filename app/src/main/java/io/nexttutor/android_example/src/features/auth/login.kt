package io.nexttutor.android_example.src.features.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.nexttutor.android_example.src.shared.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Composable
fun Login(navController: NavHostController) {
    var email by remember { mutableStateOf("ariel.mazor.mail@gmail.com") }
    var password by remember { mutableStateOf("pasword123") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = null) {
        EventBus.appLoadedEvent.onNext("")
    }

    fun signup() {
        scope.launch(Dispatchers.IO) {
            TryCatch {
                firebase.login(email, password)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log In",
            fontSize = 35.sp,
            modifier = Modifier.absolutePadding(bottom = 20.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.absolutePadding(bottom = 20.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.absolutePadding(bottom = 20.dp)
        )
        Button(onClick = { signup() }) {
            Text(text = "Log in")
        }

        Text(text = "Dont have account? signup", modifier = Modifier.clickable {
            navController.navigate("signup")
        })
    }
}