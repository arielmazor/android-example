package io.nexttutor.gPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomTab() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffe4edf4)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Cancel",
            fontSize = 18.sp,
            fontFamily = poppins,
            color = Color(0xff1f6b88),
            modifier = Modifier.padding(end = 30.dp)
        )
        Text(
            text = "Select",
            fontSize = 18.sp,
            fontFamily = poppins,
            color = Color(if (Store.selectedFilesIds.isNotEmpty()) 0xff1f6b88 else 0xff91989e),
            modifier = Modifier.padding(end = 30.dp)
        )
    }
}