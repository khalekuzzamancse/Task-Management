package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import androidx.compose.animation.scaleIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.Queue
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen.GenericScreen
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
        selectedIcon = Icons.Filled.Queue,
        unselectedIcon = Icons.Outlined.Queue,
        hasNews = false,
        badgeCount = 45
    ),
)

@Preview
@Composable
fun FriendShipNavGraphPreview() {
    val navController= rememberNavController()
    val navigateTo:(Screen)->Unit={
        navController.navigate(it.route)
    }
    FriendShipNavGraph(
        navController=navController,
        onNavigationRequest = navigateTo
    )
}

@Composable
fun FriendShipNavGraph(
    navController: NavHostController,
    onNavigationRequest:(Screen)->Unit,
) {
    var bottomNavItemSelectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    val onItemSelected: (Int) -> Unit = { index ->
        bottomNavItemSelectedItemIndex = index
        when (index) {
            0 -> onNavigationRequest(Screen.Users)
            1 -> onNavigationRequest(Screen.Friends)
            2 -> onNavigationRequest(Screen.FriendRequest)
        }

    }
    GenericScreen(
        screenTitle = "FriendShip",
        topBarNavIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onTopBarNavIconClicked = {},
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
        startDestination = Screen.Users.route
    ) {

        composable(route = Screen.Users.route) {
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
                friends = viewModel.myFriends.collectAsState().value,

                )
        }
        composable(route = Screen.FriendRequest.route) {
            val viewModel = remember {
                FriendRequestScreenViewModel()
            }
            FriendRequestList(
                modifier = Modifier,
                requests = viewModel.requests.collectAsState().value,
                onAcceptRequest = {},
                onCancelRequest = {}
            )
        }

    }
}