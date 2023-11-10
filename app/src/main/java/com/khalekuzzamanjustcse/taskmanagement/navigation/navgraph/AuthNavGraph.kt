package com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.LoginContentPreview
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.RegisterScreenPreview


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

