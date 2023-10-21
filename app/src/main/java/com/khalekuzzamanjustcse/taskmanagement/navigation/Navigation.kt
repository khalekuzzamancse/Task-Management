package com.khalekuzzamanjustcse.taskmanagement.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzamanjustcse.taskmanagement.ContactScreen
import com.khalekuzzamanjustcse.taskmanagement.FetchContact
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data.FriendRequest
import com.khalekuzzamanjustcse.taskmanagement.data.UserCollections
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.ScreenWithDrawer
import com.khalekuzzamanjustcse.taskmanagement.ui.FriendRequestListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.LoginScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.RegisterScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.User
import com.khalekuzzamanjustcse.taskmanagement.ui.UserListScreen

sealed interface Screen {
    val route: String

    data object Login : Screen {
        override val route = "Login"
    }

    data object Logout : Screen {
        override val route = "Logout"
    }

    data object Contact : Screen {
        override val route = "Contact"
    }

    data object Users : Screen {
        override val route = "Users"
    }

    data object Register : Screen {
        override val route = "Register"
    }

    data object FriendRequest : Screen {
        override val route = "FriendRequest"
    }
}


@Composable
fun NavGraph() {
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
    val context = LocalContext.current


    NavHost(
        navController = navController,
        route = "MainScreen",
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                LoginScreen(
                    onLoginButtonClicked = {

                    },
                    onNavIconClicked = openDrawer
                )
            }

        }
        composable(route = Screen.Contact.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                ContactScreen(
                    onNavIconClicked = openDrawer,
                    contacts = FetchContact(context).getContact()
                )
            }

        }
        composable(route = Screen.Users.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                var users by remember {
                    mutableStateOf(emptyList<User>())
                }
                LaunchedEffect(Unit) {
                    users = UserCollections().allUsers()
                }
                UserListScreen(onNavIconClicked = openDrawer, contacts = users) {
                    FriendRequest().addNewFriendRequest(it)
                }
            }

        }
        composable(route = Screen.Register.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                RegisterScreen()
            }

        }
        composable(route = Screen.Logout.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                AuthManager().signOut()
                LoginScreen(
                    onLoginButtonClicked = {
                    },
                    onNavIconClicked = openDrawer
                )
            }

        }
        composable(route = Screen.FriendRequest.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                var users by remember {
                    mutableStateOf(emptyList<User>())
                }

                LaunchedEffect(Unit){
                   users=FriendRequest().getFriendRequest()
                }
                FriendRequestListScreen(
                    contacts =users,
                    onNavIconClicked = {},
                    onAcceptButtonClick = {}
                )
            }

        }

    }
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: String) {
        try {
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } catch (_: Exception) {

        }
    }
}