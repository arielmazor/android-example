package io.nexttutor.gPicker

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow

const val SERVER_CLIENT_ID =
    "86595397985-s83bvf9rpk2rgrph6l3k0sm0ce12uv43.apps.googleusercontent.com"
const val SERVER_CLIENT_SECRET = "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"
const val REFRESH_TOKEN =
    "1//03yE7Pw2J-lRNCgYIARAAGAMSNwF-L9IrEZPJYNjImoTylDhgoUiARH694eflp3P29YKTe2VkD0ErCQ_4f7T8jI6YRHt-AnZMgkE"

//suspend fun getDrive(): Drive {
//    val credential = GoogleCredential.Builder()
//        .setTransport(AndroidHttp.newCompatibleTransport())
//        .setJsonFactory(JacksonFactory.getDefaultInstance())
//        .setClientSecrets(SERVER_CLIENT_ID, SERVER_CLIENT_SECRET).build()
//    credential.refreshToken = REFRESH_TOKEN
//
//    val token = Retrofit.api.getAccessToken(REFRESH_TOKEN).body()!!
//
//    credential.accessToken = token.accessToken
//    credential.expiresInSeconds = token.expirationTime
//
//    return Drive.Builder(
//        AndroidHttp.newCompatibleTransport(),
//        JacksonFactory.getDefaultInstance(),
//        credential
//    )
//        .setApplicationName("gPicker")
//        .build()
//}


class DriveService {
    companion object {
        var files = MutableStateFlow<List<File>>(mutableListOf())
    }

    suspend fun init() {
        try {
            val res = Retrofit.api.getFiles(REFRESH_TOKEN)
            files.value = res.body()!!
        } catch (err: Exception) {
            Log.e("DEBUGGER_APP", err.toString())
        }
    }
}