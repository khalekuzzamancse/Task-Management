package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
        Log.d("onRegisterSuccess", "")
    }


    navigation(
        route = Screen.AuthGraph.route,
        startDestination = Screen.Login.route
    ) {

        composable(route = Screen.Login.route) {
            val context= LocalContext.current
            val containerColor = MaterialTheme.colorScheme.surface
            val formManager = remember {
                LoginFormManager(containerColor)
            }
            var showProgressBar by remember {
                mutableStateOf(false)
            }

            viewModel.formManager = formManager
            Box (modifier=Modifier.fillMaxSize()){
                LoginFormScreen(
                    onRegisterButtonClicked = {
                        navController.navigate(Screen.Register.route)
                    },
                    onLoginRequest = {
                        showProgressBar=true
                        scope.launch {
                            val isSuccess = viewModel.tryLogin()
                            if (isSuccess) {
                                withContext(Dispatchers.Main) {
                                    onLoginSuccess()
                                    showToast(context, "Login Success")
                                    showProgressBar=false
                                }

                            }
                            else {
                                withContext(Dispatchers.Main) {
                                    showToast(context, "Login Failed")
                                    showProgressBar = false
                                }
                            }
                        }

                    },
                    viewModel = viewModel
                )
                if(showProgressBar)
                    ProgressBar(modifier = Modifier.align(Alignment.Center))
            }





        }
        composable(route = Screen.Register.route) {
            val context = LocalContext.current
            val containerColor = MaterialTheme.colorScheme.surface
            val registerFromManager = remember { RegistrationFormManager(containerColor) }
            viewModel.registrationManager = registerFromManager
            var showProgressBar by remember {
                mutableStateOf(false)
            }

            Box (modifier=Modifier.fillMaxSize()){
                RegisterScreen(
                    onBackArrowClicked = {
                        navController.popBackStack()
                    },
                    formManger = registerFromManager,
                    onRegisterCompleteRequest = {
                        showProgressBar=true
                        scope.launch {
                            val isSuccess = viewModel.tryRegister()
                            if (isSuccess) {
                                withContext(Dispatchers.Main) {
                                    onRegisterSuccess()
                                    showToast(context,"Register Successfully")
                                    showProgressBar=false
                                }

                            }
                            else{
                                withContext(Dispatchers.Main) {
                                    showToast(context, "Register Failed")
                                    showProgressBar=false
                                }
                            }
                        }

                    }
                )
                if(showProgressBar){
                    ProgressBar(modifier = Modifier.align(Alignment.Center))
                }

            }



        }
    }
}
fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
@Composable
fun ProgressBar(modifier: Modifier = Modifier){
    CircularProgressIndicator(
        color = Color.Blue,
        modifier = modifier,
    )
}



