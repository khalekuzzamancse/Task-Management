package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.contact_screens

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.NavigationActions
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.Screen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.ScreenWithDrawer
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests.FriendRequestListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests.FriendRequestScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.UserListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.UsersScreenViewModel

@Composable
fun ContactScreenNavGraph() {
    val navController = rememberNavController()
    val navigationAction = NavigationActions(navController)
    var drawerState by remember {
        mutableStateOf(DrawerState(DrawerValue.Closed))
    }
    val onCloseDrawer: () -> Unit = {
        drawerState = DrawerState(DrawerValue.Closed)
    }
    val openDrawer: () -> Unit = {
        drawerState = DrawerState(DrawerValue.Open)
    }
    val onDrawerItemClick: (String) -> Unit = {
        navigationAction.navigateTo(it)
    }

    NavHost(
        navController = navController,
        route = "ContactScreens",
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Users.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                val viewModel = remember {
                    UsersScreenViewModel()
                }
                UserListScreen(
                    contacts = viewModel.users.collectAsState().value,
                    isLoading = viewModel.isLoading.collectAsState().value,
                )
            }

        }
        composable(route = Screen.FriendRequest.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                val viewModel = remember {
                    FriendRequestScreenViewModel()
                }

                FriendRequestListScreen(
                    viewModel = viewModel
                ) {

                }

            }

        }
        composable(route = Screen.Friends.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {

                val viewModel = remember {
                    FriendListScreenViewModel()
                }

                FriendListScreen(
                    viewModel = viewModel,
                    onNavIconClicked = {},
                )

            }

        }

    }
}