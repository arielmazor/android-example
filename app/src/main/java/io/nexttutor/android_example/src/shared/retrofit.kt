package io.nexttutor.android_example.src.shared

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object retrofit {

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
            .baseUrl("http://10.0.0.4:1111")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
            .create(Api::class.java)
    }
}

@Serializable
data class DCSubscription(
    val status: String,
    val id: String,
    val canceledDate: String?
)

@Serializable
data class DCUpgradeBody(
    val uid: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val card_token: String,
    val catalog_id: String
)

@Serializable
data class CancelSubscriptionBody(
    val subscription_id: String
)

interface Api {
    @POST("/upgrade")
    suspend fun upgrade(@Body body: DCUpgradeBody): Response<Claims>

    @POST("/subscribe/cancel")
    suspend fun cancelSubscription(@Body body: CancelSubscriptionBody): Response<DCSubscription>
}