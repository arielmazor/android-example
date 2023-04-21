package io.nexttutor.android_example.src.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable

data class FbUser(
    var uid: String,
    var token: String,
)

@Serializable
data class Claims(
    val customerId: String,
    val cardId: String,
    var subscription: DCSubscription
)

object firebase {
    var fbUser: FbUser? by mutableStateOf(null)
        private set
    var claims: Claims? by mutableStateOf(null)
        private set

    init {
        subscribeAuthStateChange()
    }


    private fun subscribeAuthStateChange() {
        Firebase.auth.addAuthStateListener { fb ->
            if (fb.currentUser !== null) {
                Firebase.auth.currentUser?.getIdToken(true)?.addOnCompleteListener { tokenRes ->
                    if (tokenRes.isSuccessful) {
                        fbUser = FbUser(
                            uid = fb.currentUser!!.uid,
                            token = tokenRes.result.token!!,
                        )
                        claims = getClaims(tokenRes.result.claims)
                    }
                }
            } else {
                fbUser = null
            }
        }
    }

    fun updateClaims(claims: Claims) {
        this.claims = claims
    }

    suspend fun signup(
        email: String = "assaf1@tutorna.com",
        password: String = "Haa!123456"
    ): Boolean {
        val res = Firebase.auth.createUserWithEmailAndPassword(email, password).await()

        return res != null
    }

    fun login(email: String = "assaf1@tutorna.com", password: String = "Haa!123456") {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            debugger(it)
        }
    }

    fun logout() {
        Firebase.auth.signOut()
        fbUser = null
    }

    private fun getClaims(claims: MutableMap<String, Any>): Claims? {
        return if (claims["customerId"] != null) {
            Claims(
                customerId = claims["customerId"].toString(),
                cardId = claims["cardId"].toString(),
                subscription = DCSubscription(
                    status = claims["subscription_status"].toString(),
                    id = claims["subscription_id"].toString(),
                    canceledDate = getCanceledDate(claims["subscription_canceledDate"].toString())
                )
            )
        } else {
            null
        }
    }

    private fun getCanceledDate(canceledDate: String): String? {
        return if (canceledDate == "null") null else canceledDate
    }
}
