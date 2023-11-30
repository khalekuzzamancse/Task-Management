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
    contacts: List<User> =emptyList(),
    isLoading:Boolean,
    onNavIconClicked: () -> Unit ={},
) {

    GenericListScreen(
        items = contacts,
        isLoading = isLoading,
        itemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone) {
                if (!contact.isFriend) {
                    val icon = if (contact.isSendRequest) {
                        Icons.Filled.PersonRemove
                    } else {
                        Icons.Filled.PersonAdd
                    }
                    IconButton(
                        onClick = {
                            FriendManager().addNewFriendRequest(contact.phone)
                        },
                    ) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                }
            }
        },
        screenTitle = "Contacts",
        onBack = onNavIconClicked
    )


}
