package com.khalekuzzamanjustcse.taskmanagement.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
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
import com.khalekuzzamanjustcse.taskmanagement.FirebaseFireStore
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.ScreenWithDrawer
import com.khalekuzzamanjustcse.taskmanagement.ui.LoginScreen

sealed interface Screen {
    val route: String

    data object Login : Screen {
        override val route = "Login"
    }

    data object Contact : Screen {
        override val route = "Contact"
    }

    data object Users : Screen {
        override val route = "Users"
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
                val users by remember {
                    mutableStateOf(FirebaseFireStore().readAllDocs("Users"))
                }

                ContactScreen(onNavIconClicked = openDrawer, contacts = users)
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