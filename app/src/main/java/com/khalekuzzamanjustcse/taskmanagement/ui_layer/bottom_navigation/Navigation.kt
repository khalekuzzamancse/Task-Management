package com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation


import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.Screen



fun NavGraphBuilder.userListGraph(
) {
    navigation(
        route = "route",
        startDestination = Screen.Friends.route
    ) {
        composable(
            route =Screen.Friends.route
        ) {

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