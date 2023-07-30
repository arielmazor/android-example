package io.nexttutor.gPicker.files

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilesModel : ViewModel() {
    var files = MutableStateFlow<MutableList<File>?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val res =
                Retrofit.api.getFiles("1//03yE7Pw2J-lRNCgYIARAAGAMSNwF-L9IrEZPJYNjImoTylDhgoUiARH694eflp3P29YKTe2VkD0ErCQ_4f7T8jI6YRHt-AnZMgkE")

            withContext(Dispatchers.Main) {
                Log.d("DEBUGGER_APP", res.body()!![0].thumbnail)
                files.value = res.body()!!.toMutableList()
            }
        }
    }

}