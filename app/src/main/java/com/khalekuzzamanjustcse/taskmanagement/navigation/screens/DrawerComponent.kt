package com.khalekuzzamanjustcse.taskmanagement.navigation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.khalekuzzamanjustcse.taskmanagement.navigation.Screen


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
            ModalDrawerItem(Screen.Login.route, Icons.Filled.Login),
            ModalDrawerItem(Screen.Contact.route, Icons.Filled.Contacts),
            ModalDrawerItem(Screen.Users.route, Icons.Filled.Person),
        )
    )
    val drawerGroups = listOf(group)


}




