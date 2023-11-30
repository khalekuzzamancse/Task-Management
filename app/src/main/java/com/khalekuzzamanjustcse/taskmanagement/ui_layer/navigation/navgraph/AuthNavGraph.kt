package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.LoginContentPreview
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.RegisterScreenPreview


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess:()->Unit={}
) {
    navigation(
        route = Screen.AuthGraph.route,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginContentPreview(
                onRegisterButtonClicked = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess=onLoginSuccess

            )

        }
        composable(route = Screen.Register.route) {
            RegisterScreenPreview(
                onBackArrowClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}

