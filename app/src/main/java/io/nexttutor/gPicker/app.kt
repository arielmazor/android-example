package io.nexttutor.gPicker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.nexttutor.gPicker.files.Files
import kotlinx.coroutines.launch

val poppins = Font(R.font.poppins).toFontFamily()


@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    var sheetSnapPoint by remember { mutableStateOf(SnapPoints.Closed) }
    var isSheetClosing by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = null) {
        systemUiController.setNavigationBarColor(Color(0xffe4edf4))
        systemUiController.setSystemBarsColor(Color(0xffe4edf4))

        DriveService().setFolder()
    }

    //-----------------------------

    fun changeOrder(orderBy: OrderBy) {
        scope.launch {
            DriveService().changeOrder(orderBy)
        }
    }

    //-----------------------------

    fun onSheet1Closed(orderBy: OrderBy? = null) {
        systemUiController.setSystemBarsColor(Color(0xffe4edf4))
        sheetSnapPoint = SnapPoints.Closed
        if (orderBy != null) {
            changeOrder(orderBy)
        }
    }

    //-----------------------------

    fun setIsSheetClosing(isClosing: Boolean) {
        systemUiController.setNavigationBarColor(Color(0xffe4edf4))
        systemUiController.setSystemBarsColor(Color(0xffe4edf4))

        isSheetClosing = isClosing
    }

    //-----------------------------

    fun openSheet() {
        systemUiController.setSystemBarsColor(Color(0xff898e92))
        systemUiController.setNavigationBarColor(Color(0xffe4edf4))
        sheetSnapPoint = SnapPoints.Extended
    }

    //-----------------------------

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Header()
            Files(::openSheet)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomTab()
        }
    }
    BottomSheet(
        snapPoint = sheetSnapPoint,
        closeSheet = ::onSheet1Closed,
        isClosing = isSheetClosing,
        setIsClosing = ::setIsSheetClosing,
    )
}