package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.UserInfoCard
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User


@Composable
fun FriendRequestListScreen(
    viewModel: FriendRequestScreenViewModel,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen<User>(
        items = viewModel.users.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        itemContent = { contact  ->
            UserInfoCard(name =contact.name, phone = contact.phone, endExtraContent = {
                IconButton(
                    onClick = {},
                ) {
                    Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = null)
                }
            }, savedInContact = false)
        },
        screenTitle = "Friend Requests",
        onBack =onNavIconClicked,
    )




}
