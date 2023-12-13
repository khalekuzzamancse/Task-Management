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


    suspend fun acceptRequest(senderId: String,myUserId: String): Boolean {
        val friendShipId=getFriendShipId(senderId,myUserId)
        return if (friendShipId!=null)  update(
            Updater(
                docID = friendShipId,
                field = FIELD_FRIENDSHIP_STATE,
                value = ACCEPTED_NOT_NOTIFIED
            )
        )
        else false
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


    suspend fun getFriendRequest(userId: String): List<MyFriend> {
        val friendShips = databaseCRUD.read<FriendShipEntity>(
            collectionName = COLLECTION,
            predicate = Filter.and(
                Filter.equalTo(FIELD_RECEIVER_ID, userId),
                // Filter.lessThan(FIELD_FRIENDSHIP_STATE, 3), has bug,find why this query not work
            )
        )
            .filter { it.friendShipState != ACCEPTED_NOTIFIED && it.friendShipState != ACCEPTED_NOT_NOTIFIED }
        return toMyFriend(userId, friendShips)
    }
    suspend fun getUnNotifiedFriendRequest(userId: String): List<MyFriend> {
        val friendShips = databaseCRUD.read<FriendShipEntity>(
            collectionName = COLLECTION,
            predicate = Filter.and(
                Filter.equalTo(FIELD_RECEIVER_ID, userId),
                // Filter.lessThan(FIELD_FRIENDSHIP_STATE, 3), has bug,find why this query not work
            )
        )
            .filter { it.friendShipState== PENDING_NOT_NOTIFIED}
        return toMyFriend(userId, friendShips)
    }
    suspend fun getAcceptNotNotifiedRequest(userId: String): List<MyFriend> {
        val friendShips = databaseCRUD.read<FriendShipEntity>(
            collectionName = COLLECTION,
            predicate = Filter.and(
                Filter.equalTo(FIELD_SENDER_ID, userId),
                // Filter.lessThan(FIELD_FRIENDSHIP_STATE, 3), has bug,find why this query not work
            )
        )
            .filter { it.friendShipState== ACCEPTED_NOT_NOTIFIED}
        return toMyFriend(userId, friendShips)
    }

    suspend fun myFriendList(myUserId: String): List<MyFriend> {
        val friendShip = fetchFriends(myUserId)
        return toMyFriend(myUserId, friendShip)
    }

    private suspend fun getFriendShipId(senderId: String, receiverId: String): String? {
        return databaseCRUD.read<FriendShipEntity>(
            collectionName = COLLECTION,
            predicate = Filter.or(
                Filter.equalTo(FIELD_RECEIVER_ID, receiverId),
                Filter.equalTo(FIELD_SENDER_ID, senderId)
            )
        ).firstOrNull()?.id
    }


    private suspend fun toMyFriend(
        myUserId: String,
        friends: List<FriendShipEntity>
    ): List<MyFriend> {
        val userCollection = UserCollection()
        return friends.mapNotNull { friendShip ->
            val iAmSender = friendShip.senderId == myUserId
            val friendUserId = if (iAmSender) friendShip.receiverId else friendShip.senderId
            val user = userCollection.getUser(friendUserId)
            if (user != null) MyFriend(user.name, user.phone, friendShip.id) else null
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



