package io.nexttutor.gPicker

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

const val REFRESH_TOKEN =
    "1//04mdPNGR8XtMUCgYIARAAGAQSNwF-L9IrMzFQ6S9WvoUKsD6sa-1d04KSieVlN2Ut5Jg1KrF6E3BobKXI44KxI8pJN_Ve0vIZ7lc"


class DriveService {
    companion object {
        var nextPageToken: String? = null
        var orderBy by mutableStateOf(OrderBy.ModifiedTime)
            private set
        var files = MutableStateFlow<List<File>>(emptyList())
    }

    suspend fun setFolder(folder: File? = null) {
        withContext(Dispatchers.IO) {
            try {
                files.value = mutableListOf()
                val res =
                    Retrofit.api.setFolder(
                        REFRESH_TOKEN,
                        orderBy,
                        folder?.id ?: "",
                        nextPageToken ?: ""
                    )
                        .body()!!

                nextPageToken = res.nextPageToken
                files.value = res.files

            } catch (err: Exception) {
                Log.d("DEBUGGER_APP", err.toString())
            }
        }
    }

    suspend fun changeOrder(_orderBy: OrderBy, folder: File? = null) {
        withContext(Dispatchers.IO) {
            try {
                orderBy = _orderBy
                nextPageToken = ""
                files.value = mutableListOf()
                val res =
                    Retrofit.api.setFolder(
                        REFRESH_TOKEN,
                        _orderBy,
                        folder?.id ?: "",
                    ).body()!!

                nextPageToken = res.nextPageToken
                files.value = res.files

            } catch (err: Exception) {
                Log.d("DEBUGGER_APP", err.toString())
            }
        }
    }

    suspend fun loadMore() {
        withContext(Dispatchers.IO) {
            try {
                val res =
                    Retrofit.api.loadMore(REFRESH_TOKEN, orderBy, nextPageToken ?: "").body()!!

                nextPageToken = res.nextPageToken
                files.value += res.files
            } catch (err: Exception) {
                Log.d("DEBUGGER_APP", err.toString())
            }
        }
    }
}