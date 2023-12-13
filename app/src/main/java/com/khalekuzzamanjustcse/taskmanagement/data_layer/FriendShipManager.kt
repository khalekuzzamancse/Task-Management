package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Filter

data class FriendShipEntity @JvmOverloads constructor(
    @DocumentId val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val sentTime: Long = 0,
    val friendShipState: Int = 1,
)

data class MyFriend(
    val name: String,
    val phoneNumber: String,
)

class FriendShipManager {
    companion object {
        private const val TAG = "FriendShipManagerLog: "
        private const val COLLECTION = "FriendShips"
        private const val FIELD_RECEIVER_ID = "receiverId"
        private const val FIELD_SENDER_ID = "senderId"
        private const val FIELD_FRIENDSHIP_STATE = "friendShipState"
        private const val PENDING_NOT_NOTIFIED = 1
        private const val PENDING_NOTIFIED = 2
        private const val ACCEPTED_NOT_NOTIFIED = 3
        private const val ACCEPTED_NOTIFIED = 4
    }

    private val databaseCRUD = DatabaseCRUD()
    private fun log(message: String) {
        Log.d(TAG, message)
    }

    suspend fun makeRequestNotified(friendShipId: String): Boolean {
        return update(
            Updater(docID = friendShipId, field = FIELD_FRIENDSHIP_STATE, value = PENDING_NOTIFIED)
        )
    }

    suspend fun acceptRequest(friendShipId: String): Boolean {
        return update(
            Updater(
                docID = friendShipId,
                field = FIELD_FRIENDSHIP_STATE,
                value = ACCEPTED_NOT_NOTIFIED
            )
        )
    }

    suspend fun acceptRequestNotified(friendShipId: String): Boolean {
        return update(
            Updater(docID = friendShipId, field = FIELD_FRIENDSHIP_STATE, value = ACCEPTED_NOTIFIED)
        )
    }

    private suspend fun update(updater: Updater): Boolean {
        return databaseCRUD.update(COLLECTION, updater)
    }

    suspend fun addFriendShip(friendShip: FriendShipEntity): Boolean {
        return databaseCRUD.create(COLLECTION, friendShip)
    }


    suspend fun newFriendRequest(userId: String): List<FriendShipEntity> {
        return databaseCRUD.read(
            collectionName = COLLECTION,
            predicate = Filter.and(
                Filter.equalTo(FIELD_FRIENDSHIP_STATE, PENDING_NOT_NOTIFIED),
                Filter.equalTo(FIELD_RECEIVER_ID, userId)
            )
        )

    }

    suspend fun myFriendList(myUserId: String): List<MyFriend> {
        val friendShip = fetchFriends(myUserId)
        return toMyFriend(myUserId, friendShip)
    }

    private suspend fun toMyFriend(
        myUserId: String,
        friends: List<FriendShipEntity>
    ): List<MyFriend> {
        val userCollection = UserCollection()
        return friends.mapNotNull {
            val iAmSender = it.senderId == myUserId
            val friendUserId = if (iAmSender) it.receiverId else it.senderId
            val user = userCollection.getUser(friendUserId)
            if (user != null) MyFriend(user.name, user.phone) else null
        }
    }


    suspend fun fetchFriends(userId: String): List<FriendShipEntity> {
        val eitherRequestSenderOrReceiver = Filter.or(
            Filter.equalTo(FIELD_RECEIVER_ID, userId),
            Filter.equalTo(FIELD_SENDER_ID, userId)
        )
        return databaseCRUD.read<FriendShipEntity>(
            collectionName = COLLECTION,
            predicate = eitherRequestSenderOrReceiver
        )
            .filter { it.friendShipState == ACCEPTED_NOT_NOTIFIED || it.friendShipState == ACCEPTED_NOTIFIED }
    }

}



