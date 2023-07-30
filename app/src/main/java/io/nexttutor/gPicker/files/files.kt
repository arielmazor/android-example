package io.nexttutor.gPicker.files

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.Loader

fun formatFiles(files: List<File>): MutableList<List<File>> {
    val formattedFiles = mutableListOf<List<File>>()
    for (i in files.indices) {
        if (i % 2 == 0) {
            formattedFiles.add(files.slice(i..i + 1))
        }
    }

    return formattedFiles
}

@Composable
fun Files() {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 70
    val model = viewModel<FilesModel>()
    val files = model.files.collectAsState()

    val formattedFiles = remember(key1 = files.value) { formatFiles(files.value ?: emptyList()) }

    Box(Modifier.offset(y = (-80).dp)) {
        Loader(
            isVisible = files.value.isNullOrEmpty(),
            backgroundColor = Color.Transparent,
            spinnerSize = 35
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        items(
            items = formattedFiles, itemContent = { files ->
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .height(135.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width((screenWidth / 2).dp)) {
                        FileComponent(files[0])
                    }
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width((screenWidth / 2).dp)) {
                        FileComponent(files[1])
                    }
                }
            })
    }

}