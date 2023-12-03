package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.UserInfoCard
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User


@Composable
fun FriendListScreen(
    viewModel: FriendListScreenViewModel,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen<User>(
        items = viewModel.users.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        itemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone, savedInContact = false)
        },
        screenTitle = "My Friends",
        onBack =onNavIconClicked,

    )

}

