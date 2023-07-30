package io.nexttutor.gPicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xffe4edf4)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = "arrow left"
            )
        }
        Column {
            Text(
                text = "My Drive",
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