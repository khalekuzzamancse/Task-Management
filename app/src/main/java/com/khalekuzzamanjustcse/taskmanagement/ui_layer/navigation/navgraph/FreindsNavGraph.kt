package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.LoginFormManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.LoginFormScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.LoginViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.RegisterScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.RegistrationFormManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun NavGraphBuilder.friendsNavGraph(
    navController: NavHostController,
) {

    navigation(
        route = Screen.Friends.route,
        startDestination = Screen.Friends.route
    ) {
        composable(route = Screen.Friends.route) {
            val viewModel= FriendListScreenViewModel()
           FriendListScreen(
               viewModel =viewModel ,
               onNavIconClicked = {}
           )
        }
        composable(route = Screen.FriendRequest.route) {

        }
    }
}

