package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun FriendshipDemo() {
    val dummyFriendShip = FriendShipEntity(
        senderId = "1",
        receiverId = "2",
        friendShipState = 1,
        sentTime = 0
    )

    val docID = "DvxpN1UJrRO8IIBQHzoE"

    FlowRow {
        MyButton(onClick = {
            val isSuccess = FriendShipManager().addFriendShip(dummyFriendShip)
            Log.d("FriendShipDemo:added", "$isSuccess")
        }, label = "Add FriendShip")

        MyButton(onClick = {
            val requests = FriendShipManager().getFriendRequest("2")
            Log.d("FriendShipDemo:MyReq", "$requests")
        }, label = "My Request")

        MyButton(
            onClick = {
                val isSuccess = FriendShipManager().makeRequestNotified(docID)
                Log.d("FriendShipDemo:requestNotified", "$isSuccess")
            }, label = "RequestNotified"
        )
        MyButton(
            onClick = {
               // val isSuccess = FriendShipManager().acceptRequest(docID)
               // Log.d("FriendShipDemo:acceptRequest", "$isSuccess")
            }, label = "Accept request"
        )
        MyButton(
            onClick = {
                val isSuccess = FriendShipManager().acceptRequestNotified(docID)
                Log.d("FriendShipDemo:acceptNotified", "$isSuccess")
            }, label = "Accept Notified"
        )
        MyButton(
            onClick = {
                val friends = FriendShipManager().fetchFriends("2")
                Log.d("FriendShipDemo:friendList", "$friends")
            }, label = "Fetch Friends"
        )
        MyButton(
            onClick = {
                val friends = FriendShipManager().myFriendList("1")
                Log.d("FriendShipDemo:friendList", "$friends")
            }, label = "Read"
        )

    }
}

@Composable
fun MyButton(
    scope: CoroutineScope = rememberCoroutineScope(),
    onClick: suspend () -> Unit,
    label: String
) {
    Button(onClick = {
        scope.launch {
            onClick()
        }

    }) {
        Text(text = label)
    }
}

