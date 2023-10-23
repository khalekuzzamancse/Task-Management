package com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph

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
import com.khalekuzzamanjustcse.taskmanagement.ui.ContactScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.FetchContact
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.data.UserCollections
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.ScreenWithDrawer
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.AuthScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.FriendListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.FriendRequestListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.HomePage
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.LoginScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.RegisterScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.create_task.TaskScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes.MyTaskListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.User
import com.khalekuzzamanjustcse.taskmanagement.ui.UserListScreen


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
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                HomePage()
            }

        }
        composable(route = Screen.Login.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
              AuthScreen()
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
                UserListScreen(contacts = users, onNavIconClicked = openDrawer)
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
                LaunchedEffect(Unit) {
                    val request = FriendManager().getFriendRequest()
                    users = request.map { it.user }

                }
                FriendRequestListScreen(
                    contacts = users
                ) {}

            }

        }
        composable(route = Screen.Friends.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                var users by remember {
                    mutableStateOf(emptyList<User>())
                }

                LaunchedEffect(Unit) {
                    users = FriendManager().getFriends()
                }

                FriendListScreen(
                    contacts = users,
                    onNavIconClicked = {},
                )

            }

        }
        composable(route = Screen.Task.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                TaskScreen()

            }

        }
        composable(route = Screen.MyTask.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                MyTaskListScreen()
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