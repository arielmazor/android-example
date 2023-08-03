package io.nexttutor.gPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.nexttutor.gPicker.files.Files

val poppins = Font(R.font.poppins).toFontFamily()


@Composable
fun App() {
    val systemUiController = rememberSystemUiController()
    var currFolder by remember { mutableStateOf<File?>(null) }

    LaunchedEffect(key1 = null) {
        systemUiController.setNavigationBarColor(Color(0xffe4edf4))
        systemUiController.setSystemBarsColor(Color(0xffe4edf4))

        DriveService().init()
    }

    fun openFolder(folder: File) {
        currFolder = folder
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Header(currFolder)
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