package io.nexttutor.gPicker.files

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nexttutor.Icon
import io.nexttutor.gPicker.DriveService
import io.nexttutor.gPicker.poppins
import io.nexttutor.icons
import io.nexttutor.noRippleClickable

@Composable
fun FormatHeader(openSheet: () -> Unit) {
    val name = when {
        DriveService.orderBy.name.contains("Modified") -> "Last modified"
        DriveService.orderBy.name.contains("Name") -> "Name"
        else -> "Last Opened"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(94.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.noRippleClickable { openSheet() }) {
            Text(
                text = name,
                fontFamily = poppins,
                fontSize = 17.sp,
                color = Color(0xff333333)
            )
            Icon(
                icons.ArrowDownward,
                Modifier
                    .size(25.dp)
                    .padding(start = 5.dp)
                    .rotate(if (DriveService.orderBy.name.contains("Asc")) 180f else 0f),
                Color(0xff333333)
            )
        }
    }
}