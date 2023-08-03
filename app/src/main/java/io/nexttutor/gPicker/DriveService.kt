package io.nexttutor.gPicker

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow

const val REFRESH_TOKEN =
    "1//04bkxcKc-vYP5CgYIARAAGAQSNwF-L9IrMsJgxtGtEWsdUQXwF7UyFo_iajtNWRPArbd3MFNAsceErHu6xviFhUPqXZwVxEc0T5k"


class DriveService {
    companion object {
        var foldersNextPageToken: String? = null
        var folders = MutableStateFlow<List<Folder>>(mutableListOf())
        var filesNextPageToken: String? = null
        var files = MutableStateFlow<List<File>>(mutableListOf())
    }

    suspend fun init() {
        try {
            val res = Retrofit.api.getInitial(REFRESH_TOKEN).body()!!
            foldersNextPageToken = res.foldersNextPageToken
            folders.value = res.folders
            filesNextPageToken = res.filesNextPageToken
            files.value = res.files
        } catch (err: Exception) {
            Log.e("DEBUGGER_APP", err.toString())
        }
    }
}