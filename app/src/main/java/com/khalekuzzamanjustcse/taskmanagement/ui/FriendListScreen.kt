package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.runtime.Composable
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@Composable
fun FriendListScreen(
    contacts: List<User>,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = contacts,
        itemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone)
        },
        screenTitle = "My Friends",
        onBack =onNavIconClicked
    )

}

