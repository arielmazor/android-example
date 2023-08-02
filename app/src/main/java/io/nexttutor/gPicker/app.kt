package io.nexttutor.gPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.api.services.drive.model.File
import io.nexttutor.gPicker.files.Files
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val poppins = Font(R.font.poppins).toFontFamily()


@Composable
fun App() {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = null) {
        systemUiController.setNavigationBarColor(Color(0xffe4edf4))
        systemUiController.setSystemBarsColor(Color(0xffe4edf4))

        DriveService().init()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Header()
            FormatHeader()
            Files()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomTab(false)
        }
    }
}