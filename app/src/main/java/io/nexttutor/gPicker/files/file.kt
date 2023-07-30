package io.nexttutor.gPicker.files

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.nexttutor.gPicker.File
import io.nexttutor.gPicker.R
import io.nexttutor.gPicker.poppins

//https://lh3.googleusercontent.com/fife/AKsag4MZwzyBYaAKIvlAwaoaMESWa_MgSo7nrJDgubzr2VWHy_9qdrkGaeM7SA7Q1ysOj19shOZeQO8G1VUi3_26nG3cxayqzrq3tGUg0Rwhmc5TmEqH33CnznlaFienKx0t67cF9nZ6euCak3X-8rcd-G_S8ocOc3MGZWmhbtQLJEo2r-LPwRSOekanIBvvelIHB8iCe6OxLX5KIMZOxHTkm-V9GU_ph-nL_5hHX5LEWNyny0XlHlTPZGKAA0Of63jjPR7T04Y_k4jzJwbmzLhq6o5Y2ImQqvm-ox70g8IMMjpFXW310cYdIwX3e3J2SnsqMav4nj3OfvWsuwAPIEvHuCFoagNBokWk-yXLYYr5VPlc0tvJNSwCct8Fcp5ebu_mY8Y6uLxiW3-Nu-LgCoTVsF1waLTnIiQ57qWN6kCjfDquNgUxKWHbkuKl4T8n8bgykjkezGBAdLB5Q06oYvKm1V__V7_t8Dr32jwckbTcNBlAflllQJBM1yVJaRWpN8WFJCDcTC3bVbFd-U97UOyPaYZbucXse7I53_6dpXbLEaK7CZeLq39NwO1o63_OfvFJc06ZLYE6sTU_OO6iWHGC0GaxM-EP4F-41JcUYQXR6bfNFudCv8gyttk4TF3-nD_7guGoni-_RxhbBxtjuqkTRGYTGsNEw99rp-kmKq4Gj6ScdQhmjDa4LTRkBQj-BZAbYyszfzRrC4RFYbn34yxxhIVUHJLeHoVOjnPlFZ-VndvxA_wR9gyMLKwYVgdT7F_j-SqPlMzPVH0PR5SBq0h8fv8fqp3OZoMs2J2-4bXZpkUqsChzvjO5RXT9I7AvthLjW1mwGl-NTcJZCMzmF8FLTmrXVqQZI2Lgjw58da6DTrfp6WLi4mN5TdSa_Pyqq9zSbXQQTyh1moervvgHDdv9uiUIfCnuDvRnAr2cTYJujbM_tT6K2dIufyxusX7BJtStAJIoNwKhOnY03ljhdPfpHxxoLksf26g1U9GuLiLFAJFVPNcWv35Su672wwrM_OsT5I7EB1AcNkcsxxNkmdUqVVElialDrYexYP7F9DYRpghz1Q6nxS6gq3oXDlHBAb2SS0OVcWufvHhLJMkwInwQjOEriD7tTk5Kyg6GpECp0Cc6Dk2fdmGSG1-GT5IeumuWAQCgOazcaTW66Eu524Q6qm6dmErvmT3tlw2-UWItKKH866vFvS52D0_LrjSHtj_vx3T5BpMrHnLcNvIaZRRTzvXFIAOQTtaUesBsTvA6AX0bwywzBH9N8sswG09KuEfEvAyMaZWPJCli4sPvnOcltZqudk_UNN1lneUN3qJmBP3SqQ4oRVBUs9XIVEZGSR_TO1d_Xpt7WEJqcYcR4FG5eTyWCt2rOC7QMcULX2I0EXFf6fWqVSL5RaxsJGfnWjc3VsmJ2SRZ6mxwUpp8sTSC8nVfjHnMFxp2-W6sqUTlLEPL4mnvB3YjpAlSHMfkhFhy2DfeQ70dUhOJwusdCm-BTeBFnqUhUFN4WIBAoVZF9drRHt5c35g6WaHXQ8zMxY4h-Xh91QNA83pE3DoKJMTDwLPSYBIxaWT6pzWpmoalO2R6Xj3y5wkIAHTjVX_1ZICvgpbtGidYFTP7zbICm80TZyTxPv8Pqwk4_47x4uvG1ROqbSypJoGcauzuUV6n2A=w640?authuser=0
@Composable
fun FileComponent(file: File) {
    LaunchedEffect(key1 = null) {
//        Log.d("IMAGE_APP", "https://drive.google.com/thumbnail?sz=w640&id=${file.id}")
        Log.d("IMAGE_APP", file.toString())
    }

    Column(Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(file.thumbnail),
            contentDescription = "thumbnail",
            modifier = Modifier.size(50.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.menu), contentDescription = "MIME")
            Text(
                text = file.name,
                fontSize = 16.sp,
                fontFamily = poppins,
                color = Color(0xff333333)
            )
        }
    }
}