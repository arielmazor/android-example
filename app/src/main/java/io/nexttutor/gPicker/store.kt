package io.nexttutor.gPicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Store {
    var selectedFilesIds by mutableStateOf(listOf<String>())
    var selectedFolders = mutableStateListOf<File?>(null)
}