package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object AuthManager {
    var onLogout: () -> Unit = {}
    private var auth = Firebase.auth
    private var user = auth.currentUser
    private const val TAG = "AuthManagerLog: "
    private val _loggedIn = MutableStateFlow(user!=null)
    var loggedIn = _loggedIn.asStateFlow()
        private set
init {

    auth = Firebase.auth
     user = auth.currentUser
    _loggedIn.value =user!=null
    loggedIn = _loggedIn.asStateFlow()
}
    private fun log(message: String) {
        Log.d(TAG, message)
    }


    suspend fun createAccount(email: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        log("createUserWithEmailAndPassword():success")
//                        log("createUserWithEmailAndPassword():CurrentUser:${auth.currentUser}")
                        continuation.resume(true)
                    } else {
                        // log("createUserWithEmailAndPassword():failure")
                        continuation.resume(false)
                    }
                }
        }
    }

    suspend fun signIn(email: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        log("signInWithEmailAndPassword():success")
//                        log("signInWithEmailAndPassword():CurrentUser:${auth.currentUser}")
                        continuation.resume(true) // Call the continuation with success
                        _loggedIn.value=true
                    } else {
                        //log("signInWithEmailAndPassword():failure")
                        continuation.resume(false)
                        _loggedIn.value=false
                    }
                }
        }
    }

    fun singedInUserEmail(): String? {
        val user = auth.currentUser
        if (user != null) {
            return user.email
        }
        return null
    }

    suspend fun signedInUserPhone(): String? {
        return try {
            user?.let {
                val email = it.email
                if (email != null) {
                    UserCollection().getUserByEmail(email)?.phone
                } else null
            }
        } catch (e: Exception) {
            null
        }
    }


    fun signOut() {
        auth.signOut()
        onLogout()
        _loggedIn.value=false
    }
}