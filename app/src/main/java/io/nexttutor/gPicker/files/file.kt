package io.nexttutor.gPicker.files

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.nexttutor.Icon
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.R
import io.nexttutor.gPicker.Store
import io.nexttutor.gPicker.poppins
import io.nexttutor.icons
import io.nexttutor.noRippleClickable

@Composable
fun FileComponent(file: File, loadMore: (() -> Unit)?) {
    val isSelected = remember(Store.selectedFilesIds) {
        Store.selectedFilesIds.find { file.id == it } != null
    }

    val painter = when {
        file.mimeType.contains("pdf") -> painterResource(id = R.drawable.pdf)
        file.mimeType.contains("application/vnd.google-apps.document") -> painterResource(id = R.drawable.docs)
        file.mimeType.contains("folder") -> painterResource(id = R.drawable.folder)
        file.mimeType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document") -> painterResource(
            id = R.drawable.word
        )

        else -> painterResource(id = R.drawable.slides)
    }

    val background by animateColorAsState(
        targetValue = if (!isSelected) {
            Color(0xffffffff)
        } else Color(0xffd2e6f5)
    )

    fun onPress() {
        if (isSelected) {

            Store.selectedFilesIds = Store.selectedFilesIds.filter { it != file.id }
        } else {
            Store.selectedFilesIds += file.id
        }
    }

    LaunchedEffect(key1 = null) {
        loadMore?.invoke()
    }

    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(0.95f)
            .height(60.dp)
            .background(background, RoundedCornerShape(20.dp))
            .noRippleClickable { onPress() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp),
            contentAlignment = Alignment.Center
        ) {
            if (file.thumbnailLink.isNotEmpty() && !file.thumbnailLink.contains("https://docs.google.com") && !file.mimeType.contains(
                    "pdf"
                ) && !file.mimeType.contains("vnd.openxml")
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(file.thumbnailLink)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
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
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(background),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icons.CheckCircle, Modifier.size(25.dp), Color(0xff04688b))
                }
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

@Composable
fun FolderComponent(folder: File, openFolder: (File) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(60.dp)
            .noRippleClickable { openFolder(folder) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.folder),
                contentDescription = "folder",
                modifier = Modifier.size(25.dp),
            )
        }
        Text(
            text = folder.name,
            fontSize = 17.sp,
            fontFamily = poppins,
            color = Color(0xff333333),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(0.8f),
            maxLines = 1
        )
    }
}