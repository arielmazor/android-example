package io.nexttutor.gPicker


import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.nexttutor.Icon
import io.nexttutor.icons
import io.nexttutor.noRippleClickable
import kotlinx.coroutines.launch


enum class SnapPoints {
    Closed,
    Extended
}

@Composable
fun BottomSheet(
    snapPoint: SnapPoints,
    closeSheet: (OrderBy?) -> Unit,
    isClosing: Boolean,
    setIsClosing: (Boolean) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp + 100

    val offset by animateDpAsState(
        targetValue = if (!isClosing) {
            when (snapPoint) {
                SnapPoints.Extended -> {
                    (screenHeight * 0.55).dp
                }

                SnapPoints.Closed -> screenHeight.dp
            }
        } else screenHeight.dp,
        animationSpec = TweenSpec(durationMillis = 300),
        finishedListener = {
            if (it.value > 400 && isClosing) {
                setIsClosing(false)
                closeSheet(null)
            }
        }
    )

    val alpha by animateFloatAsState(
        targetValue = if (!isClosing && snapPoint != SnapPoints.Closed) 0.4f else 0f,
        TweenSpec(durationMillis = 300)
    )

    if (snapPoint != SnapPoints.Closed) {

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha))
            .noRippleClickable { setIsClosing(true) })

        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable { setIsClosing(true) },
                verticalAlignment = Alignment.Bottom
            ) {

                Column(
                    modifier = Modifier
                        .zIndex(10f)
                        .offset(y = offset)
                        .fillMaxSize()
                        .background(
                            Color(0xffe4edf4),
                            RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                        )
                        .noRippleClickable { }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .drawBehind {
                                val height = size.height - 1.dp.toPx() / 2

                                drawLine(
                                    color = Color(0xffbfcbd1),
                                    start = Offset(x = 0f, y = height),
                                    end = Offset(x = size.width, y = height),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                            .padding(start = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sort by",
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            color = Color(0xff333333),
                            modifier = Modifier.offset(y = 2.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .height(
                                if (!isClosing) when (snapPoint) {
                                    SnapPoints.Extended -> ((screenHeight * 0.6) - 100).dp
                                    else -> screenHeight.dp
                                } else screenHeight.dp
                            )
                    ) {
                        SortItem(OrderBy.Name, DriveService.orderBy, closeSheet)
                        SortItem(OrderBy.ModifiedTime, DriveService.orderBy, closeSheet)
                        SortItem(OrderBy.OpenedTime, DriveService.orderBy, closeSheet)
                    }
                }
            }
        }

    }
}


@Composable
fun SortItem(type: OrderBy, currType: OrderBy, close: (OrderBy) -> Unit) {
    val scope = rememberCoroutineScope()
    val isActive = currType.name.contains(type.name)
    val name = when (type) {
        OrderBy.Name -> "Name"
        OrderBy.OpenedTime -> "Last opened"
        else -> "Last modified"
    }

    fun onPress() {
        val newType = when {
            isActive -> when (currType) {
                OrderBy.ModifiedTime -> OrderBy.ModifiedTimeAsc
                OrderBy.ModifiedTimeAsc -> OrderBy.ModifiedTime
                OrderBy.Name -> OrderBy.NameAsc
                OrderBy.NameAsc -> OrderBy.Name
                OrderBy.OpenedTime -> OrderBy.OpenedTimeAsc
                OrderBy.OpenedTimeAsc -> OrderBy.OpenedTime
            }

            else -> type
        }
        close(newType)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.98f)
            .height(60.dp)
            .background(
                Color(if (isActive) 0xffd1e5f4 else 0xffe4edf4),
                RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp)
            )
            .noRippleClickable { onPress() }
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icons.ArrowDownward,
            Modifier
                .padding(end = 10.dp)
                .size(25.dp)
                .rotate(if (currType.name.contains("Asc")) 180f else 0f),
            Color(if (isActive) 0xff333333 else 0xffe1ecf2)
        )
        Text(
            text = name,
            fontFamily = poppins,
            fontSize = 16.sp,
            color = Color(0xff333333)
        )
    }
}