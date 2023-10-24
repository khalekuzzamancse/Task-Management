package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friends

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friends.FriendListScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.users.User
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@Composable
fun FriendListScreen(
    viewModel: FriendListScreenViewModel,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = viewModel.users.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        itemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone)
        },
        screenTitle = "My Friends",
        onBack =onNavIconClicked,

    )

}

