package com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.items

@Preview
@Composable
fun ReplaceAbleContentScreenPreview() {
    val navController = rememberNavController()
    var bottomNavItemSelectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    val onItemSelected: (Int) -> Unit = { index ->
        bottomNavItemSelectedItemIndex = index
        when (index) {
            0 -> navController.navigate("ScreenA")
            1 -> navController.navigate("ScreenB")
            2 -> navController.navigate("ScreenC")
        }

    }

    TopNBottomBarDecorator(
        screenTitle = "Demo",
        topBarNavIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onTopBarNavIconClicked = {},
        bottomNavigationItems = items,
        bottomNavItemSelectedItemIndex = bottomNavItemSelectedItemIndex,
        onBottomNavItemSelected = onItemSelected
    ) {
        ContentReplaceAbleContainer(modifier = it, navController)
    }

}

@Composable
fun ContentReplaceAbleContainer(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = "DemoNavGraph",
        startDestination = "ScreenA"
    ) {
        composable(route = "ScreenA") {
            DemoScreen(title = "A", color = Color.White)
        }
        composable(route = "ScreenB") {
            DemoScreen(title = "B",color = Color.Red)
        }
        composable(route = "ScreenC") {
            DemoScreen(title = "C",color = Color.Blue)
        }
    }
}

@Composable
fun DemoScreen(
    title: String,
    color: Color
) {
    val isLightBackground = color.luminance() > 0.5
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "This is Screen :$title",
            color = if (isLightBackground) Color.Black else Color.White,
        )
    }

}