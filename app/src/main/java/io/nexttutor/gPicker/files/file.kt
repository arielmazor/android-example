package io.nexttutor.gPicker.files

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.R
import io.nexttutor.gPicker.poppins

@Composable
fun FileComponent(file: File) {
    val painter = when {
        file.MIME.contains("pdf") -> painterResource(id = R.drawable.pdf)
        file.MIME.contains("document") -> painterResource(id = R.drawable.docs)
        file.MIME.contains("folder") -> painterResource(id = R.drawable.folder)
        else -> painterResource(id = R.drawable.slides)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp),
            contentAlignment = Alignment.Center
        ) {
            if (file.thumbnail.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(file.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(35.dp)
                        .border(
                            BorderStroke(
                                width = 1.dp,
                                color = Color(0xffdadadc)
                            ), RoundedCornerShape(4.dp)
                        ),
                )
            } else {
                Image(
                    painter = painter,
                    contentDescription = "thumbnail",
                    modifier = Modifier.size(25.dp),
                )
            }
        }
        Text(
            text = file.name,
            fontSize = 17.sp,
            fontFamily = poppins,
            color = Color(0xff333333),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(0.8f),
            maxLines = 1
        )
    }
}