package io.nexttutor.gPicker

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


object Retrofit {

    @OptIn(ExperimentalSerializationApi::class)
    val api: Api by lazy {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://3285-2a10-8012-11-6ca1-9d5c-6e92-a4c9-fa1b.ngrok-free.app")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
            .create(Api::class.java)
    }
}

@Serializable
data class File(
    val id: String,
    val name: String,
    val thumbnail: String,
    val MIME: String
)

@Serializable
data class Token(
    val accessToken: String,
    val expirationTime: Long
)

interface Api {
    @GET("/files")
    suspend fun getFiles(@Query("refresh_token") refresh_token: String): Response<List<File>>

    @GET("/access_token")
    suspend fun getAccessToken(@Query("refresh_token") refresh_token: String): Response<Token>
}