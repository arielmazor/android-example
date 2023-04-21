package io.nexttutor.android_example.src.shared

import android.util.Log
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> TryCatch(block: suspend () -> T) =
    try {
        block()
    } catch (err: IOException) {
        debugger(err)
    } catch (err: HttpException) {
        debugger(err)
    } catch (err: Exception) {
        debugger(err)
    }

fun debugger(content: Any, tag: String? = "DEBBUGGER_APP") {
    when (content) {
        is Exception -> Log.e(tag, content.toString())
        is IOException -> Log.e(tag, content.message!!)
        is HttpException -> Log.e(tag, "HttpException, unexpected response")
        is String -> Log.d(tag, content)
    }
}