package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendRequest
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.ProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen.GenericListComposable

@Composable
fun FriendRequestList(
    modifier: Modifier,
    requests: List<FriendRequest>,
    onAcceptRequest: (String) -> Unit,
    onCancelRequest: (String) -> Unit,
) {
    GenericListComposable(
        modifier = modifier
//            .fillMaxSize()
//            .padding(start = 16.dp, end = 16.dp)
        ,
        items = requests,
        itemContent = { request ->
            FriendRequestCard(
                name = request.friendName,
                phone = request.friendPhone,
                isSentByMe = request.isSentByMe,
                onAccept = {
                    onAcceptRequest(request.friendShipId)
                },
                onCancel = {
                    onCancelRequest(request.friendShipId)
                }
            )

        })
}


@Composable
fun FriendRequestListScreen(
    viewModel: FriendRequestScreenViewModel,
    onAcceptRequest: (String) -> Unit,
    onCancelRequest: (String) -> Unit,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = viewModel.requests.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        itemContent = { request ->
            FriendRequestCard(
                name = request.friendName,
                phone = request.friendPhone,
                isSentByMe = request.isSentByMe,
                onAccept = {
                    onAcceptRequest(request.friendShipId)
                },
                onCancel = {
                    onCancelRequest(request.friendShipId)
                }
            )
        },
        screenTitle = "Friend Requests",
        onBack = onNavIconClicked,
    )


}

@Composable
fun FriendRequestCard(
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
    isSentByMe: Boolean,
    onAccept: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
//        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier,
//                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage()
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = phone,
                    style = MaterialTheme.typography.labelSmall
                )

            }
            Spacer(modifier = Modifier.weight(1f))

            if (isSentByMe) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel")
                }
            } else {
                TextButton(onClick = onAccept) {
                    Text(text = "Accept")
                }
            }


        }

    }

}