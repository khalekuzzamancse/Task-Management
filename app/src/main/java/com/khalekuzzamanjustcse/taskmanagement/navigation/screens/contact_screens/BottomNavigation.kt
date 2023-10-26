package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.contact_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph.Screen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.ProfileImage


@Composable
fun ContactScreens(
    navigateTo: (Screen) -> Unit
) {
    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.Users)
    }
    val navigationIcon =
        if (currentScreen == Screen.Users) Icons.Filled.Menu else Icons.Filled.ArrowBack


    Scaffold(
        topBar = {
            ContactScreensTopBar(
                title = "",
                navigationIcon = navigationIcon,
                onNavigationIconClick = {}
            )
        },
        bottomBar = {
            ContactScreensBottomNavigation(
                navigateTo = {
                    currentScreen = it
                    navigateTo(it)

                })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreensTopBar(
    title: String,
    navigationIcon: ImageVector = Icons.Filled.Menu,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(navigationIcon, null)
            }
        },
        actions = {
            ProfileImage()
        }
    )

}

@Composable
private fun ContactScreensBottomNavigation(navigateTo: (Screen) -> Unit) {
    var selected by remember {
        mutableIntStateOf(1)
    }
    BottomAppBar {
        NavigationBarItem(
            selected = selected == 1,
            onClick = {
                selected = 1
                navigateTo(Screen.Users)
            },
            icon = {
                Icon(Icons.Filled.Contacts, null)
            },
            label = {
                Text(text = "All Users")
            }

        )
        NavigationBarItem(
            selected = selected == 2,
            onClick = {
                selected = 2
                navigateTo(Screen.FriendRequest)
            },
            icon = {
                Icon(Icons.Filled.PersonAddAlt1, null)
            },
            label = {
                Text(text = "Friends Request")
            }

        )
        NavigationBarItem(
            selected = selected == 3,
            onClick = {
                selected = 3
                navigateTo(Screen.Friends)
            },
            icon = {
                Icon(Icons.Filled.PeopleAlt, null)
            },
            label = {
                Text(text = "My Friends")
            }
        )
    }


}