package com.khalekuzzamanjustcse.taskmanagement.data_layer.notification

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.Filter
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCollectionReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipEntity
import com.khalekuzzamanjustcse.taskmanagement.data_layer.MyFriend
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
data class FriendShipInfo(
    val friendPhone: String,
    val friendName: String,
    val friendStatus: Int,
    val friendShipId: String,
    val iAmSender: Boolean
)

@Preview
@Composable
fun Demo() {

    LaunchedEffect(Unit) {
        ObservableFriendShip._friendShipWithMe.collect {
            Log.d("friendShipStatus:D", "${it}")
        }

    }
    LaunchedEffect(Unit){
        ObservableFriendShip.observer("01625337883")
    }

}


object ObservableFriendShip {

        private const val TAG = "FriendShipManagerLog: "
        private const val COLLECTION = "FriendShips"
        private const val FIELD_RECEIVER_ID = "receiverId"
        private const val FIELD_SENDER_ID = "senderId"
        private const val FIELD_FRIENDSHIP_STATE = "friendShipState"
        private const val PENDING_NOT_NOTIFIED = 1
        private const val PENDING_NOTIFIED = 2
        private const val ACCEPTED_NOT_NOTIFIED = 3
        private const val ACCEPTED_NOTIFIED = 4


     val _friendShipWithMe = MutableStateFlow(emptyList<FriendShipInfo>())



    private suspend fun onFriendShipChanged(myUserId: String, friendShips: List<FriendShipEntity>) {
        val shipInfo= friendShips.mapNotNull {
            val shipInfo = getFriendShipInfo(myUserId, it)
            //Log.d("friendShipStatus:Ob", "${shipInfo}")
            shipInfo

        }
        _friendShipWithMe.value=shipInfo
//        Log.d("friendShipStatus:OFlow", "${_friendShipWithMe.value}")

    }

    private suspend fun getFriendShipInfo(
        myUserId: String, friendShip: FriendShipEntity
    ): FriendShipInfo? {
        val userCollection = UserCollection()
        val iAmSender = friendShip.senderId == myUserId
        val friendUserId = if (iAmSender) friendShip.receiverId else friendShip.senderId
        val user = userCollection.getUser(friendUserId)
        return if (user != null) FriendShipInfo(
            friendName = user.name,
            friendPhone = user.phone,
            friendShipId = friendShip.id,
            friendStatus = friendShip.friendShipState,
            iAmSender = iAmSender
        ) else null
    }


    suspend fun observer(userId: String) {
        val eitherRequestSenderOrReceiver = Filter.or(
            Filter.equalTo(FIELD_RECEIVER_ID, userId),
            Filter.equalTo(FIELD_SENDER_ID, userId)
        )
        DatabaseCollectionReader(COLLECTION)
            .readObservable(
                classType = FriendShipEntity::class.java,
                where = eitherRequestSenderOrReceiver
            ).collect {
                onFriendShipChanged(userId, it)

            }
    }

}
