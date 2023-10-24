package com.khalekuzzamanjustcse.taskmanagement.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class AuthManager(
    private val onSuccess: () -> Unit = {},
    private val onFailed: () -> Unit = {},
) {
    private val auth = Firebase.auth
    private val user = auth.currentUser
    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                }
            }


    }

    fun signIn(
        email: String,
        password: String,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                    Log.i("CurrentUser: ", "signInWithEmail:success")
                } else {
                    onFailed()
                    Log.i("CurrentUser: ", "signInWithEmail:fail")
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
    }
}