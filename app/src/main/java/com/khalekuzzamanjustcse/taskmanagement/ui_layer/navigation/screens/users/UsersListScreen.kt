package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendManager
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
    UserListScreen(
        listOf(
            User("Mr Bean A", "000000000"),
            User("Mr Bean B", "11111111"),
            User("Mr Bean C", "22222222", isFriend = true),
            User("Mr Bean D", "33333333333", isFriend = false, isSendRequest = true),
        ),
        onNavIconClicked = {},
        isLoading = true
    )
}

@Composable
fun UserListScreen(
    users: List<User> = emptyList(),
    isLoading: Boolean,
    onFriendRequestSent :(User) -> Unit = {},
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
                    if (!user.isFriend) {
                        val icon = if (user.isSendRequest) {
                            Icons.Filled.PersonRemove
                        } else {
                            Icons.Filled.PersonAdd
                        }
                        IconButton(
                            onClick = {
                             onFriendRequestSent(user)
                            },
                        ) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    }
                },
                savedInContact = user.isAsLocalContact,

            )
        },
        screenTitle = "Users",
        onBack = onNavIconClicked
    )


}
