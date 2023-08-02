package io.nexttutor.gPicker.files

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import io.nexttutor.gPicker.DriveService
import io.nexttutor.gPicker.Loader

@Composable
fun Files() {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 70
    val files = DriveService.files.collectAsState()

    Box(Modifier.offset(y = (-80).dp)) {
        Loader(
            isVisible = files.value.isEmpty(),
            backgroundColor = Color.Transparent,
            spinnerSize = 35
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        items(items = files.value, itemContent = { file ->
            FileComponent(file)
        })
    }

}