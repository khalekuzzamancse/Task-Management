package com.khalekuzzanman.just.cse.friend.data


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzanman.just.cse.database.DatabaseCRUD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@Preview
@Composable
fun Demo() {
    LaunchedEffect(Unit) {
       DatabaseCRUD().read()
//        Log.d("FreindModule","$res")
    }
}


data class FriendShipEntity constructor(
    val id: String = "",
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

data class FriendShipInfo(
    val friendPhone: String,
    val friendName: String,
    val friendStatus: Int,
    val friendShipId: String,
    val iAmSender: Boolean
)

data class FriendRequest(
    val friendShipId: String,
    val friendPhone: String,
    val friendName: String,
    val isSentByMe: Boolean,
)


//@Preview
//@Composable
//fun Demo() {
//
//    LaunchedEffect(Unit) {
//        ObservableFriendShip.friendShipWithMe.collect {
//
//        }
//    }
//    LaunchedEffect(Unit) {
//        ObservableFriendShip.myFriends.collect {
//            Log.d("friendShipStatus:Friend", "${it}")
//        }
//    }
//    LaunchedEffect(Unit) {
//        ObservableFriendShip.requests.collect {
//            Log.d("friendShipStatus:Req", "${it}")
//        }
//    }
//    LaunchedEffect(Unit) {
//        ObservableFriendShip.observer("01571378537")
//    }
//
//}


enum class FriendShipStatus {
    Pending, Friend, NoFriendShip
}

object FriendShipObserver {

    private const val TAG = "FriendShipManagerLog: "
    private const val COLLECTION = "FriendShips"
    private const val FIELD_RECEIVER_ID = "receiverId"
    private const val FIELD_SENDER_ID = "senderId"
    private const val PENDING_NOT_NOTIFIED = 1
    private const val ACCEPTED_NOT_NOTIFIED = 3


    private val _friendShipWithMe = MutableStateFlow(emptyList<FriendShipInfo>())
    val friendShipWithMe = _friendShipWithMe.asStateFlow()
    private val _myFriends = MutableStateFlow(emptyList<FriendShipInfo>())
    val myFriends = _myFriends.asStateFlow()
    private val _requests = MutableStateFlow(emptyList<FriendRequest>())
    val requests = _requests.asStateFlow()


    private suspend fun onFriendShipChanged(myUserId: String, friendShips: List<FriendShipEntity>) {
        val shipInfo = friendShips
            .mapNotNull { getFriendShipInfo(myUserId, it) }
        //
        notify(shipInfo)
        updateFriendshipInfo(shipInfo)
        updateRequestInfo(shipInfo)
        updateFriendList(shipInfo)

//        Log.d("friendShipStatus:OFlow", "${_friendShipWithMe.value}")
    }

    private suspend fun notify(shipInfo: List<FriendShipInfo>) {
        shipInfo.forEach { friendShipInfo ->
            if (friendShipInfo.friendStatus == PENDING_NOT_NOTIFIED) {
                notifyNewFriendRequest(friendShipInfo)
            } else if (friendShipInfo.iAmSender && friendShipInfo.friendStatus == ACCEPTED_NOT_NOTIFIED) {
                notifyRequestAccept(friendShipInfo)
            }

        }
    }

    private suspend fun notifyNewFriendRequest(info: FriendShipInfo) {
        val title = "New Friend Request"
        val message = "By ${info.friendName} (${info.friendPhone})"
        // BaseApplication.notify(title, message)
        // FriendShipManager().makeRequestNotified(info.friendShipId)
    }

    private suspend fun notifyRequestAccept(info: FriendShipInfo) {
        val title = "Friend Request Accepted"
        val message = "By ${info.friendName} (${info.friendPhone})"
        //  BaseApplication.notify(title, message)
        //
        //  FriendShipManager().acceptRequestNotified(info.friendShipId)
    }

    private fun updateFriendshipInfo(shipInfo: List<FriendShipInfo>) {
        _friendShipWithMe.value = shipInfo
    }

    private fun updateRequestInfo(shipInfo: List<FriendShipInfo>) {
        _myFriends.value = shipInfo.filter { it.friendStatus >= 3 }
    }

    private fun updateFriendList(shipInfo: List<FriendShipInfo>) {
        _requests.value = shipInfo.filter { it.friendStatus <= 2 }.map {
            FriendRequest(
                friendShipId = it.friendShipId,
                friendPhone = it.friendPhone,
                friendName = it.friendName,
                isSentByMe = it.iAmSender
            )
        }
    }


    private suspend fun getFriendShipInfo(
        myUserId: String, friendShip: FriendShipEntity
    ): FriendShipInfo? {
//        val userCollection = UserCollection()
//        val iAmSender = friendShip.senderId == myUserId
//        val friendUserId = if (iAmSender) friendShip.receiverId else friendShip.senderId
//        val user = userCollection.getUser(friendUserId)
//        return if (user != null) FriendShipInfo(
//            friendName = user.name,
//            friendPhone = user.phone,
//            friendShipId = friendShip.id,
//            friendStatus = friendShip.friendShipState,
//            iAmSender = iAmSender
//        ) else null
        return null
    }


    suspend fun subscribe(userId: String) {
//        val eitherRequestSenderOrReceiver = Filter.or(
//            Filter.equalTo(FIELD_RECEIVER_ID, userId),
//            Filter.equalTo(FIELD_SENDER_ID, userId)
//        )
//        DatabaseCollectionReader(COLLECTION)
//            .readObservable(
//                classType = FriendShipEntity::class.java,
//                where = eitherRequestSenderOrReceiver
//            ).collect {
//                onFriendShipChanged(userId, it)
//
//            }
    }

}
