package com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.device_contact.ContactScreen
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.ScreenWithDrawer
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.AuthScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friends.FriendListScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friend_requests.FriendRequestListScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.create_task.TaskScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.device_contact.DeviceContactViewModel
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friend_requests.FriendRequestScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.friends.FriendListScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.home.Home
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes.TaskDetailsScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.users.UserListScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.users.UsersScreenViewModel


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
    val navigateToCreateTaskScreen: () -> Unit = {
        navigationAction.navigateTo(Screen.Task.route)
    }
    var task = remember {
        TaskEntity()
    }
    val onTaskDetailsOpen: (TaskEntity) -> Unit = {
        task = it
        navigationAction.navigateTo(Screen.MyTaskDetails.route)
    }
    val onTaskDetailsClose: () -> Unit = {
        navigationAction.navigateTo(Screen.Home.route)
    }


    val context = LocalContext.current


    NavHost(
        navController = navController,
        route = "MainScreen",
        startDestination = Screen.Login.route
    ) {

        composable(route = Screen.Home.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                Home(
                    openDrawer = openDrawer,
                    onCreateTask = navigateToCreateTaskScreen,
                    onTaskDetailsOpen = onTaskDetailsOpen,
                    onLogOut = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }

        }
        composable(route = Screen.Login.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                AuthScreen(onLoginSuccess = {
//                    navController.popBackStack()
                    navigationAction.navigateTo( Screen.Home.route)
                })
            }

        }
        composable(route = Screen.Contact.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {

                val viewModel = remember {
                    DeviceContactViewModel(context)
                }
                ContactScreen(
                    viewModel = viewModel,
                    onNavIconClicked = openDrawer,
                )
            }

        }
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

        composable(route = Screen.Task.route) {
            TaskScreen(
                onBackArrowClick = {
                    navController.popBackStack()
                })
        }

        composable(route = Screen.MyTaskDetails.route) {
            TaskDetailsScreen(task = task, onClose = onTaskDetailsClose)
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