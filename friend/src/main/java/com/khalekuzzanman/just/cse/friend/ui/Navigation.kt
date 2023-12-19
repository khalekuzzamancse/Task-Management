package com.khalekuzzanman.just.cse.friend.ui


import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.Screen
import com.khalekuzzanman.just.cse.friend.ui.screen.friends.FriendListScreen


fun NavGraphBuilder.userListGraph(
) {
    navigation(
        route = "route",
        startDestination = Screen.Friends.route
    ) {
        composable(
            route =Screen.Friends.route
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