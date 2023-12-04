package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthManager(
    private val onLogout: () -> Unit = {},
) {
    private val auth = Firebase.auth
    private val user = auth.currentUser

    companion object {
        private const val TAG = "AuthManagerLog: "
        private fun log(message: String){
            Log.d(TAG, message)
        }
    }

//    fun createAccount(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                } else {
//                }
//            }
//
//    }
    suspend fun createAccount(email: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        log("createUserWithEmailAndPassword():success")
                        log("createUserWithEmailAndPassword():CurrentUser:${auth.currentUser}")
                        continuation.resume(true)
                    } else {
                        log("createUserWithEmailAndPassword():failure")
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
                        log("signInWithEmailAndPassword():success")
                        log("signInWithEmailAndPassword():CurrentUser:${auth.currentUser}")
                        continuation.resume(true) // Call the continuation with success
                    } else {
                        log("signInWithEmailAndPassword():failure")
                        continuation.resume(false) // Call the continuation with failure
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
                val phone = FirebaseFireStore().getUserPhoneNumber(email)
                phone // Return the phone number
            }
        } catch (e: Exception) {
            null
        }
    }


    fun signOut() {
        auth.signOut()
        onLogout()
    }
}