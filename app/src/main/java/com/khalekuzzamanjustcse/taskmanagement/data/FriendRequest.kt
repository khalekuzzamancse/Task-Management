package com.khalekuzzamanjustcse.taskmanagement.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.khalekuzzamanjustcse.taskmanagement.ui.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FriendRequest {
    private val db = Firebase.firestore
    fun addNewFriendRequest(phoneNoTo: String) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val email = AuthManager().singedInUserEmail()
            val phone = FirebaseFireStore().getUserPhoneNumber(email)
            if (phone != null) {
                FirebaseFireStore().addFriendRequest(phone, phoneNoTo)
            }
        }
    }

    suspend fun getFriendRequest(): List<User> {
        val email = AuthManager().singedInUserEmail()
        val signInUserPhoneNo = FirebaseFireStore().getUserPhoneNumber(email)
        val users = mutableListOf<User>()
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection("FriendRequest")
                    .whereEqualTo("to", signInUserPhoneNo)
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    val requestFrom = document.getString("from")
                    if (requestFrom != null) {
                        val user = UserCollections().user(requestFrom)
                        user?.let {
                            users.add(it)
                        }
                    }
                }

            }
            users
        } catch (_: Exception) {
            users
        }
    }
}