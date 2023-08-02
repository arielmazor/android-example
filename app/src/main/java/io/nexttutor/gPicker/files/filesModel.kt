package io.nexttutor.gPicker.files

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

const val query =
    "trashed = false and 'me' in owners"

class FilesModel : ViewModel() {
    val files = MutableStateFlow<MutableList<File>>(mutableListOf())


    fun init(drive: Drive) {

        viewModelScope.launch(Dispatchers.Default) {
            val result = drive.Files().list().apply {
                orderBy = "modifiedTime desc"
                fields = "files(id, name, thumbnailLink)"
                q = "trashed = false and 'me' in owners"
            }.execute()
            files.value = result.files
        }
    }

}
