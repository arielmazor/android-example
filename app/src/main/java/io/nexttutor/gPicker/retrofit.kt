package io.nexttutor.gPicker

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


object Retrofit {

    @OptIn(ExperimentalSerializationApi::class)
    val api: Api by lazy {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://27f0-2a10-8012-5-52b3-e1b6-8a15-329b-74d2.ngrok-free.app")
            .addConverterFactory(json.asConverterFactory(contentType))
            .addConverterFactory(EnumConverterFactory())
            .client(client)
            .build()
            .create(Api::class.java)
    }

    class EnumConverterFactory : Converter.Factory() {
        override fun stringConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<*, String>? {
            if (type is Class<*> && type.isEnum) {
                return Converter<Any?, String> { value -> getSerializedNameValue(value as Enum<*>) }
            }
            return null
        }
    }

    fun <E : Enum<*>> getSerializedNameValue(e: E): String {
        try {
            return e.javaClass.getField(e.name).getAnnotation(SerialName::class.java)!!.value
        } catch (exception: NoSuchFieldException) {
            exception.printStackTrace()
        }

        return ""
    }
}

@Serializable
enum class OrderBy {
    @SerialName("modifiedTime desc")
    ModifiedTime,

    @SerialName("modifiedTime")
    ModifiedTimeAsc,

    @SerialName("name_natural desc")
    Name,

    @SerialName("name_natural")
    NameAsc,

    @SerialName("viewedByMeTime desc")
    OpenedTime,

    @SerialName("viewedByMeTime")
    OpenedTimeAsc,
}

@Serializable
data class File(
    val id: String,
    val name: String,
    val thumbnailLink: String = "",
    val mimeType: String,
    var isSelected: Boolean = false
)


@Serializable
data class MoreFiles(
    val nextPageToken: String,
    val files: MutableList<File>
)

interface Api {
    @GET("/drive/folder")
    suspend fun setFolder(
        @Query("refreshToken") refreshToken: String,
        @Query("orderBy") orderBy: OrderBy,
        @Query("folderId") folderId: String = "",
        @Query("pageToken") pageToken: String = ""
    ): Response<MoreFiles>

    @GET("/drive/more")
    suspend fun loadMore(
        @Query("refreshToken") refreshToken: String,
        @Query("orderBy") orderBy: OrderBy,
        @Query("pageToken") pageToken: String
    ): Response<MoreFiles>
}