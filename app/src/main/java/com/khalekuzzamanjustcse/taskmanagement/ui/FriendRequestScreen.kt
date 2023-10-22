package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@Composable
fun FriendRequestListScreen(
    contacts: List<User>,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = contacts,
        itemContent = { contact ->
            UserInfoCard(name =contact.name, phone = contact.phone ){
                IconButton(
                    onClick = {},
                ) {
                    Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = null)
                }
            }
        },
        screenTitle = "Contacts",
        onNavIconClicked =onNavIconClicked
    )




}
