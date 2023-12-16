package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.BottomNavigationItem
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen.TopNBottomBarDecorator
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests.FriendRequestList
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests.FriendRequestScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendList
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.UserList
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.UsersScreenViewModel


val friendshipDestinations = listOf(
    BottomNavigationItem(
        label = "Users",
        selectedIcon = Icons.Filled.PermIdentity,
        unselectedIcon = Icons.Outlined.PermIdentity,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "Friends",
        selectedIcon = Icons.Filled.Group,
        unselectedIcon = Icons.Outlined.Group,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "Requests",
        selectedIcon = Icons.Filled.PersonAdd,
        unselectedIcon = Icons.Outlined.PersonAdd,
        hasNews = false,
        badgeCount = 45
    ),
)

@Preview
@Composable
fun FriendShipNavGraphPreview() {
    val navController = rememberNavController()
    val navigateTo: (Screen) -> Unit = {
        navController.navigate(it.route)
    }
    val navigateBack: () -> Unit = {

    }
    FriendShipNavGraph(
        onBackToPrevious = navigateBack
    )
}

@Composable
fun FriendShipNavGraph(
    onBackToPrevious: () -> Unit
) {
    val navController = rememberNavController()
    val onNavigationRequest: (Screen) -> Unit = {
        navController.navigate(it.route)
    }
    var bottomNavItemSelectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var showTopNavigation by remember {
        mutableStateOf(true)
    }


    val onItemSelected: (Int) -> Unit = { index ->
        bottomNavItemSelectedItemIndex = index

        // Now you can use previousBottomNavItemSelectedItemIndex to access the previous index
        when (index) {
            0 -> {
                onNavigationRequest(Screen.Connections)
                showTopNavigation = true
            }

            1 -> {
                onNavigationRequest(Screen.Friends)
                showTopNavigation = false
            }

            2 -> {
                onNavigationRequest(Screen.FriendRequest)
                showTopNavigation = false
            }
        }
    }
    TopNBottomBarDecorator(
        screenTitle = "FriendShip",
        topBarNavIcon = if (showTopNavigation) Icons.AutoMirrored.Filled.ArrowBack else null,
        onTopBarNavIconClicked = {
            onBackToPrevious()
        },
        bottomNavigationItems = friendshipDestinations,
        bottomNavItemSelectedItemIndex = bottomNavItemSelectedItemIndex,
        onBottomNavItemSelected = onItemSelected
    ) {
        FriendShipNavGraph(modifier = it, navController)
    }

}


@Composable
fun FriendShipNavGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = navController,
        route = "DemoNavGraph",
        startDestination = Screen.Connections.route
    ) {

        composable(route = Screen.Connections.route) {
            val viewModel = remember {
                UsersScreenViewModel(context.contentResolver)
            }
            UserList(
                modifier = Modifier,
                users = viewModel.users.collectAsState().value,
                onFriendRequestSent = viewModel::onFriendRequestSent
            )
        }
        composable(route = Screen.Friends.route) {
            val viewModel = remember {
                FriendListScreenViewModel()
            }
            FriendList(
                modifier = Modifier,
                friends = viewModel.myFriends.collectAsState().value
            )
        }
        composable(route = Screen.FriendRequest.route) {
            val viewModel = remember {
                FriendRequestScreenViewModel()
            }
            FriendRequestList(
                modifier = Modifier,
                requests = viewModel.requests.collectAsState().value,
                onAcceptRequest = viewModel::acceptFriendRequest,
                onCancelRequest = {}
            )
        }

    }
}