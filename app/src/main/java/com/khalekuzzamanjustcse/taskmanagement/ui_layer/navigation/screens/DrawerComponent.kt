package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.Screen


data class ModalDrawerItem(
    val label: String,
    val icon: ImageVector,
)

data class ModalDrawerGroup(
    val name: String,
    val members: List<ModalDrawerItem>,
)

object DrawerItemsProvider {
    private val group = ModalDrawerGroup(
        name = "",
        members = listOf(
            ModalDrawerItem(Screen.Home.route, Icons.Filled.Home),
            ModalDrawerItem(Screen.Contact.route, Icons.Filled.Contacts),
            ModalDrawerItem(Screen.Users.route, Icons.Filled.Person),
            ModalDrawerItem(Screen.FriendRequest.route, Icons.Filled.Logout),

        )
    )
    val drawerGroups = listOf(group)


}




