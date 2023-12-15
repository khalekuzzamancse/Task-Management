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
    val phone: String,
    val friendShipId: String = "",
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


    suspend fun acceptRequest(friendShipId:String): Boolean {
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
    suspend fun sentFriendRequests(senderId: String, receiverId: String): Boolean{
       return addFriendShip(FriendShipEntity(senderId=senderId, receiverId=receiverId))
    }

    suspend fun addFriendShip(friendShip: FriendShipEntity): Boolean {
        return databaseCRUD.create(COLLECTION, friendShip)
    }






    suspend fun fetchFriends(userId: String): List<FriendShipEntity> {
        val eitherRequestSenderOrReceiver = Filter.or(
            Filter.equalTo(FIELD_RECEIVER_ID, userId),
            Filter.equalTo(FIELD_SENDER_ID, userId)
        )
        return databaseCRUD.read<FriendShipEntity>(
            collection = COLLECTION,
            where = eitherRequestSenderOrReceiver
        )
            .filter { it.friendShipState == ACCEPTED_NOT_NOTIFIED || it.friendShipState == ACCEPTED_NOTIFIED }
    }

}



