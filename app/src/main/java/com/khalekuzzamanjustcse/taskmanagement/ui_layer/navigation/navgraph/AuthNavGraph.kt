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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit = {},
) {

    val viewModel = LoginViewModel()
    val scope = CoroutineScope(Dispatchers.IO)

    val onRegisterSuccess: () -> Unit = {
        navController.navigate(Screen.Login.route)
    }


    navigation(
        route = Screen.AuthGraph.route,
        startDestination = Screen.Login.route
    ) {

        composable(route = Screen.Login.route) {
            val containerColor = MaterialTheme.colorScheme.surface
            val formManager = remember {
                LoginFormManager(containerColor)
            }
            viewModel.formManager = formManager



            LoginFormScreen(
                onRegisterButtonClicked = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginRequest = {
                    scope.launch {
                        val isSuccess = viewModel.tryLogin()
                        if (isSuccess) {
                            withContext(Dispatchers.Main) {
                                onLoginSuccess()
                            }

                        }
                    }

                },
                viewModel = viewModel
            )

        }
        composable(route = Screen.Register.route) {
            val containerColor = MaterialTheme.colorScheme.surface
            val registerFromManager = remember { RegistrationFormManager(containerColor) }
            viewModel.registrationManager = registerFromManager
            RegisterScreen(
                onBackArrowClicked = {
                    navController.popBackStack()
                },
                formManger = registerFromManager,
                onRegisterCompleteRequest = {
                    scope.launch {
                        val isSuccess = viewModel.tryRegister()
                        if (isSuccess) {
                            withContext(Dispatchers.Main) {
                                onRegisterSuccess()
                            }

                        }
                    }

                }
            )
        }
    }
}

