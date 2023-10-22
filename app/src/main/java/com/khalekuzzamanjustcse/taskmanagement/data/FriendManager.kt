package com.khalekuzzamanjustcse.taskmanagement.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.khalekuzzamanjustcse.taskmanagement.ui.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class FriendRequest(
    val user: User,
    val hasNotified: Boolean
)

class FriendManager {
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

    fun addNewFriend(otherPhoneNo: String) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val email = AuthManager().singedInUserEmail()
            val singedUserPhoneNo = FirebaseFireStore().getUserPhoneNumber(email)
            if (singedUserPhoneNo != null) {
                val friend = hashMapOf(
                    "user1Id" to singedUserPhoneNo,
                    "user2Id" to otherPhoneNo,
                    "notified" to false,
                )
                db.collection("Friends").document("$otherPhoneNo-$singedUserPhoneNo")
                    .set(friend)
                    .addOnSuccessListener {
                        Log.i("FriendAdded: ", "Successfully")
                    }
                    .addOnFailureListener { }
            }
        }
    }

    suspend fun getFriendRequest(): List<FriendRequest> {
        val email = AuthManager().singedInUserEmail()
        val signInUserPhoneNo = FirebaseFireStore().getUserPhoneNumber(email)
        val requests = mutableListOf<FriendRequest>()
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection("FriendRequest")
                    .whereEqualTo("receiver", signInUserPhoneNo)
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    val requestFrom = document.getString("sender")
                    val hasNotified = document.getBoolean("hasBeenNotified")
                    if (requestFrom != null&&hasNotified!=null) {
                        val user = UserCollections().user(requestFrom)
                        user?.let {it->
                            requests.add(FriendRequest(it,hasNotified))
                        }
                    }
                }

            }
            requests
        } catch (_: Exception) {
            requests
        }
    }

    suspend fun getFriends(): List<User> {
        val email = AuthManager().singedInUserEmail()
        val signInUserPhoneNo = FirebaseFireStore().getUserPhoneNumber(email)
        val users = mutableListOf<User>()
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection("Friends")
                    .where(
                        Filter.or(
                            Filter.equalTo("user1Id", signInUserPhoneNo),
                            Filter.equalTo("user2Id", signInUserPhoneNo)
                        )
                    )
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    val user1Id = document.getString("user1Id")
                    val user2Id = document.getString("user2Id")
                    if (user1Id != null && user2Id != null) {
                        val friendIdOfCurrentUser =
                            if (user1Id != signInUserPhoneNo) user1Id else user2Id
                        val user = UserCollections().user(friendIdOfCurrentUser)
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