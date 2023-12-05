package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User
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
    companion object {
        private const val TAG = "FriendManagerLog: "

    }

    private fun log(message: String) {
        Log.d(TAG, message)
    }

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

    fun acceptFriendRequest(requestSenderPhone: String) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            getSingedUserPhone()?.let { singedUserPhoneNo ->
                addToFriendTable(
                    singedUserPhoneNo = singedUserPhoneNo,
                    senderPhoneNo = requestSenderPhone
                )
                removeFriendRequest(
                    senderPhone = requestSenderPhone,
                    receiverPhone = singedUserPhoneNo
                )
            }

        }
    }

    private fun removeFriendRequest(senderPhone: String, receiverPhone: String) {
        db.collection("FriendRequest").document("$senderPhone$receiverPhone")
            .delete()
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    private fun addToFriendTable(
        singedUserPhoneNo: String,
        senderPhoneNo: String
    ) {
        val friend = hashMapOf(
            "user1Id" to singedUserPhoneNo,
            "user2Id" to senderPhoneNo,
            "notified" to false,
        )
        db.collection("Friends").document("$senderPhoneNo-$singedUserPhoneNo")
            .set(friend)
            .addOnSuccessListener {
                Log.i("FriendAdded: ", "Successfully")
            }
            .addOnFailureListener { }
    }

    private suspend fun getSingedUserPhone(): String? {
        val email = AuthManager().singedInUserEmail()
        return FirebaseFireStore().getUserPhoneNumber(email)
    }

    suspend fun getReceivedFriendRequest(): List<FriendRequest> {
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
                    if (requestFrom != null && hasNotified != null) {
                        val user = UserCollections().user(requestFrom)
                        user?.let { it ->
                            requests.add(FriendRequest(it, hasNotified))
                        }
                    }
                }

            }
            requests
        } catch (_: Exception) {
            requests
        }
    }

    suspend fun getSentFriendRequest(): List<FriendRequest> {
        val email = AuthManager().singedInUserEmail()
        val signInUserPhoneNo = FirebaseFireStore().getUserPhoneNumber(email)
        val requests = mutableListOf<FriendRequest>()
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection("FriendRequest")
                    .whereEqualTo("sender", signInUserPhoneNo)
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    val requestedTo = document.getString("receiver")
                    val hasNotified = document.getBoolean("hasBeenNotified")
                    if (requestedTo != null && hasNotified != null) {
                        val user = UserCollections().user(requestedTo)
                        user?.let { it ->
                            requests.add(FriendRequest(it, hasNotified))
                        }
                    }
                }

            }
            requests
        } catch (_: Exception) {
            requests
        }
    }

    suspend fun friendRequestAcceptNotified(
        friendPhoneNo: String
    ) {


    }

    suspend fun getNewFriends(): List<User> {
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
                    ).whereEqualTo("notified", false)
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    val user1Id = document.getString("user1Id")
                    val user2Id = document.getString("user2Id")
                    if (user1Id != null && user2Id != null) {
                        val friendIdOfCurrentUser =
                            if (user1Id != signInUserPhoneNo) user2Id else user1Id
                        log("friendPhone:$friendIdOfCurrentUser")
                        val user = UserCollections().user(friendIdOfCurrentUser)
                        user?.let {
                            users.add(it)
                                //prevent to get multiple notifications for the same friendship
                            notified(document.id)
                        }
                    }
                }

            }
            log("friends:$users")
            users
        } catch (_: Exception) {
            users
        }

    }

    private fun notified(documentId: String) {
        db
            .collection("Friends")
            .document(documentId)
            .update("notified", true)
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