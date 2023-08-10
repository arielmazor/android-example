package io.nexttutor.gPicker.files

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.Loader
import io.nexttutor.gPicker.REFRESH_TOKEN
import io.nexttutor.gPicker.Retrofit
import io.nexttutor.gPicker.Store

@Composable
fun Folder() {
    var files: List<File>? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = null) {
//        files = Retrofit.api.getFilesByFolderId(REFRESH_TOKEN, Store.selectedFolder!!.id).body()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(Modifier.offset(y = (-80).dp)) {
            Loader(
                isVisible = files == null,
                backgroundColor = Color.Transparent,
                spinnerSize = 35
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = files ?: emptyList()) { file ->
                FileComponent(file, null)
            }
        }
    }
}