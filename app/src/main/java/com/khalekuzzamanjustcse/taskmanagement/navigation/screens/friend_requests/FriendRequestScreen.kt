package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friend_requests

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.users.User
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@Composable
fun FriendRequestListScreen(
    viewModel: FriendRequestScreenViewModel,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = viewModel.users.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        itemContent = { contact ->
            UserInfoCard(name =contact.name, phone = contact.phone ){
                IconButton(
                    onClick = {},
                ) {
                    Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = null)
                }
            }
        },
        screenTitle = "Friend Requests",
        onBack =onNavIconClicked,
    )




}
