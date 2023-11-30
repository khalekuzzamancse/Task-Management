package com.khalekuzzamanjustcse.taskmanagement.ui_layer.nav_demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Preview
@Composable
fun NavDemo() {
    val navController = rememberNavController()
    val userInfoScreenRef = "userInfo"
    val loginScreenRef = "login"

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {

        NavHost(
            navController = navController,
            startDestination = loginScreenRef,
            builder = {

                composable(
                    route = "$userInfoScreenRef/{name}/{phone}",
                    arguments = listOf(
                        navArgument("name") { type = NavType.StringType },
                        navArgument("phone") { type = NavType.StringType },
                    ),

                    content = { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name")
                        val phone =backStackEntry.arguments?.getString("phone")
                        Text(text = "User Name: $name\nPhone: $phone")
                    }

                )
                composable(
                    route = loginScreenRef,
                    content = {
                        Text(text = "Login Screen")
                    }
                )

            })
        navController.navigate("$userInfoScreenRef/Md Kalam Azad/017388-13865")

    }

}

