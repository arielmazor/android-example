package io.nexttutor.gPicker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nexttutor.Icon
import io.nexttutor.icons
import io.nexttutor.noRippleClickable

@Composable
fun Header(currFolder: File?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xffe4edf4)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
            if (currFolder == null) {
                Icon(icons.ArrowBack, Modifier.noRippleClickable {
                    Log.d("DEBUGGER_APP", "My Drive")
                })
            } else {
                Icon(icons.Close, Modifier.noRippleClickable {
                    Log.d("DEBUGGER_APP", currFolder.toString())
                })
            }
        }
        Column {
            Text(
                text = currFolder?.name ?: "My Drive",
                fontSize = 21.sp,
                fontFamily = poppins,
                color = Color.Black
            )
            Text(
                text = "Choose an item",
                fontSize = 16.sp,
                fontFamily = poppins,
                color = Color.Black,
                modifier = Modifier.offset(y = (-2).dp)
            )
        }
    }
}