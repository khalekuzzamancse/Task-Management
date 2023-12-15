package com.khalekuzzanman.just.cse.friend.ui


import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.khalekuzzanman.just.cse.friend.ui.screen.friends.FriendListScreen

sealed class Screen {
    open val route: String = ""
    open val parameterNames: List<String> = emptyList()

    data object MyFriends : Screen() {
        override val route = "MyFriends"
    }

    data object FriendRequest : Screen() {
        override val route = "FriendRequest"
    }
}

fun NavGraphBuilder.userListGraph(
) {
    navigation(
        route = "route",
        startDestination = Screen.MyFriends.route
    ) {
        composable(
            route = Screen.MyFriends.route
        ) {
            FriendListScreen(
                friends = emptyList(),
                onNavIconClicked = {}
            )
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
//