package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipStatus
import com.khalekuzzamanjustcse.taskmanagement.data_layer.user_managment.User2
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.UserInfoCard


data class User(
    val name: String,
    val phone: String,
    val isFriend: Boolean = false,
    val isSendRequest: Boolean = false,
    val email: String = "",
    val isAsLocalContact: Boolean = false
)

@Preview
@Composable
fun UserListScreenPreview() {
//    UserListScreen(
//        listOf(
//            User("Mr Bean A", "000000000"),
//            User("Mr Bean B", "11111111"),
//            User("Mr Bean C", "22222222", isFriend = true),
//            User("Mr Bean D", "33333333333", isFriend = false, isSendRequest = true),
//        ),
//        onNavIconClicked = {},
//        isLoading = true
//    )
}

@Composable
fun UserListScreen(
    users: List<User2> = emptyList(),
    isLoading: Boolean,
    onFriendRequestSent: (User2) -> Unit = {},
    onNavIconClicked: () -> Unit = {},
) {
    GenericListScreen(
        items = users,
        isLoading = isLoading,
        itemContent = { user ->
            UserInfoCard(
                name = user.name,
                phone = user.phone,
                endExtraContent = {
                    val requestPending = user.friendShipStatus == FriendShipStatus.Pending
                    val isNotFriend = user.friendShipStatus == FriendShipStatus.NoFriendShip
                    if (requestPending) {
                        Text(text = "Pending")
                    } else if (isNotFriend) {
                        TextButton(onClick = {
                            onFriendRequestSent(user)
                        }) {
                            Text(text = "Request")
                        }
                    }
                },
                savedInContact = user.localContact,

                )
        },
        screenTitle = "Users",
        onBack = onNavIconClicked
    )


}
