package io.nexttutor.gPicker.files

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.nexttutor.gPicker.DriveService
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.Loader
import io.nexttutor.gPicker.Store
import kotlinx.coroutines.launch

@Composable
fun Files(openSheet: () -> Unit) {
    val scope = rememberCoroutineScope()
    val files = DriveService.files.collectAsState()
    var isLoadingMore by remember { mutableStateOf(false) }
    var currIndex by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = currIndex)

    fun loadMore(index: Int) {
        scope.launch {
            isLoadingMore = true
            DriveService().loadMore()
            isLoadingMore = false
            currIndex = index
        }
    }

    //-------------------------------------

    fun openFolder(folder: File) {
        scope.launch {
            Store.selectedFolders.add(folder)
            DriveService().setFolder(folder)
        }
    }

    //-------------------------------------
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
        ) {
            item {
                FormatHeader(openSheet)
            }
            itemsIndexed(items = files.value) { index, file ->
                if (file.mimeType.contains("folder")) {
                    FolderComponent(file, ::openFolder)

                } else {

                    FileComponent(
                        file,
                        if (files.value.lastIndex == index && !isLoadingMore && DriveService.nextPageToken?.isNotEmpty() == true) {
                            { loadMore(index) }
                        } else null
                    )
                }
            }
            item {
                if (isLoadingMore) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Gray),
                    ) {
                        Loader(spinnerSize = 25)
                    }
                }
            }
        }
        Box(
            Modifier
                .offset(y = 150.dp)
                .fillMaxHeight(0.6f)
        ) {
            Loader(
                isVisible = files.value.isEmpty(),
                backgroundColor = Color.Transparent,
                spinnerSize = 35,
                modifier = Modifier.offset(y = (-45).dp)
            )
        }
    }
}