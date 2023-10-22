package com.khalekuzzamanjustcse.taskmanagement.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.khalekuzzamanjustcse.taskmanagement.ui.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserCollections {
    private val db = Firebase.firestore
    private val collectionReference = Firebase.firestore.collection("Users")

    suspend fun allUsers(): List<User> {
        val users = mutableListOf<User>()
        try {
            val querySnapshot = collectionReference.get().await()
            for (document in querySnapshot) {
                val name = document.getString("Name")
                val phoneNumber = document.getString("PhoneNumber")
                val email = document.getString("Email")
                if (name != null && phoneNumber != null && email != null) {
                    users.add(User(name = name, phone = phoneNumber, email = email))
                }
            }
        } catch (_: Exception) {
        }
        return users.filter { it.email != AuthManager().singedInUserEmail().toString() }
    }

    suspend fun user(phone: String): User? {
        try {
            val document = collectionReference.document(phone).get().await()
            val name = document.getString("Name")
            val phoneNumber = document.getString("PhoneNumber")
            if (name != null && phoneNumber != null) {
                return User(name = name, phone = phoneNumber)
            }
        } catch (_: Exception) {
        }
        return null
    }
}


class FirebaseFireStore {
    private val db = Firebase.firestore
    fun readAllDocs(collectionName: String): List<User> {
        val users = mutableListOf<User>()
        db.collection(collectionName)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (doc in value!!) {

                    val name = doc.getString("Name")
                    val phoneNumber = doc.getString("PhoneNumber")
                    if (name != null && phoneNumber != null) {
                        users.add(User(name = name, phone = phoneNumber))
                    }

                }
            }
        return users
    }


    fun addUser(email: String, phone: String, name: String) {
        val user = hashMapOf(
            "PhoneNumber" to phone,
            "Email" to email,
            "Name" to name
        )
        db.collection("Users").document(phone)
            .set(user)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun addFriendRequest(sender: String, receiver: String) {
        val requestEdge = hashMapOf(
            "sender" to sender,
            "receiver" to receiver,
            "hasBeenNotified" to false
        )
        db.collection("FriendRequest").document(sender + receiver)
            .set(requestEdge)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }


    suspend fun getUserPhoneNumber(email: String?): String? {
        if (email == null)
            return null
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection("Users")
                    .whereEqualTo("Email", email)
                    .get()
                    .await()
            }

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first()
                document.id
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getDocuments(
        collectionName: String,
        whereClause: Filter,
    ) {
        try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection(collectionName)
                    .where(whereClause)
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents
            }
        } catch (_: Exception) {

        }
    }

    suspend fun getFriendRequest(currentUserPhone: String?): String? {
        if (currentUserPhone == null)
            return null
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                db.collection("FriendRequest")
                    .whereEqualTo("to", currentUserPhone)
                    .get()
                    .await()
            }
            if (!querySnapshot.isEmpty) {

                for (document in querySnapshot.documents) {
                    Log.i("FriendRequestEdge", "$document")
                }
                val document = querySnapshot.documents.first()
                document.id
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}


