package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.khalekuzzamanjustcse.taskmanagement.DeepLink
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedByMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedToMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskAssignedToMe
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.ScreenWithDrawer
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task.CreateTaskFormManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task.CreateTaskViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task.TaskScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.ContactScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.DeviceContactViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests.FriendRequestListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests.FriendRequestScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends.FriendListScreenViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.home.Home
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskAssignedByMeDetails
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskDetailsScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskOwnedByMe
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.UserListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.UsersScreenViewModel
import kotlinx.coroutines.launch


@Composable
fun NavGraph(

) {
    val navController: NavHostController = rememberNavController()
    val navigationAction = NavigationActions(navController)
    val scope = rememberCoroutineScope()
    var drawerState by remember {
        mutableStateOf(DrawerState(DrawerValue.Closed))
    }
    val onCloseDrawer: () -> Unit = {
        drawerState = DrawerState(DrawerValue.Closed)
    }
    val openDrawer: () -> Unit = {
        drawerState = DrawerState(DrawerValue.Open)
    }
    val onDrawerItemClick: (String) -> Unit = {
        navigationAction.navigateTo(it)
    }
    val navigateToCreateTaskScreen: () -> Unit = {
        navigationAction.navigateTo(Screen.Task.route)
    }

    var taskToOpen: TaskAssignedToMe? = remember { null }
    val onTaskDetailsOpen: (TaskAssignedToMe) -> Unit = { task ->
        taskToOpen = task
//        Log.d("onTaskDetailsOpen", "$task")
        navigationAction.navigateTo("MyTaskDetails/${task.taskId}")
        // navigationAction.navigateTo("MyTaskDetails")
    }

    var myOwnedTaskToOpen: TaskOwnedByMe? by remember { mutableStateOf(null) }
    val onMyOwnedTaskOpenDetail: (TaskOwnedByMe) -> Unit = { task ->
        scope.launch {
            AssignedByMeTasksObserver.taskDetails(task.taskId).collect {
                myOwnedTaskToOpen = it
            }
        }
        navigationAction.navigateTo(Screen.MyOwnedTaskDetails.route)
    }

    val onTaskDetailsClose: () -> Unit = {
        navigationAction.navigateTo(Screen.Home.route)
    }


    val context = LocalContext.current


    val isLogin = AuthManager.loggedIn.collectAsState().value

    NavHost(
        navController = navController,
        route = "MainGraph",
        startDestination = if (isLogin) Screen.Home.route else Screen.AuthGraph.route
    ) {

        //auth nav graph
        authNavGraph(
            navController = navController,
            onLoginSuccess = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )

        //
        composable(route = Screen.Home.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                Home(
                    openDrawer = openDrawer,
                    onCreateTask = navigateToCreateTaskScreen,
                    onTaskDetailsOpen = onTaskDetailsOpen,
                    onLogOut = {
                        AuthManager.signOut()
                        navController.navigate(Screen.Login.route)
                    },
                    onMyOwnedTaskOpenDetail = onMyOwnedTaskOpenDetail
                )

            }

        }

        composable(route = Screen.Contact.route) {

            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {

                val viewModel = remember {
                    DeviceContactViewModel(context)
                }
                ContactScreen(
                    viewModel = viewModel,
                    onNavIconClicked = openDrawer,
                )
            }

        }
        composable(route = Screen.Users.route) {
            rememberCoroutineScope()
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                val viewModel = remember {
                    UsersScreenViewModel(context.contentResolver)
                }

                UserListScreen(
                    users = viewModel.users.collectAsState().value,
                    isLoading = viewModel.isLoading.collectAsState().value,
                    onFriendRequestSent = viewModel::onFriendRequestSent
                )
            }

        }

        composable(route = Screen.FriendRequest.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {
                val viewModel = remember {
                    FriendRequestScreenViewModel()
                }

                FriendRequestListScreen(
                    viewModel = viewModel,
                    onAcceptRequest = viewModel::acceptFriendRequest,
                    onCancelRequest = {},
                ) {

                }

            }

        }
        composable(route = Screen.Friends.route) {
            ScreenWithDrawer(
                drawerState = drawerState,
                closeDrawer = onCloseDrawer,
                onDrawerItemClick = onDrawerItemClick
            ) {

                val viewModel = remember {
                    FriendListScreenViewModel()
                }

                FriendListScreen(
                    viewModel = viewModel,
                    onNavIconClicked = {
                        navController.popBackStack()
                    },
                )

            }

        }

        composable(route = Screen.Task.route) {
            val containerColor = MaterialTheme.colorScheme.surface
            val viewModel = remember {
                CreateTaskViewModel(CreateTaskFormManager(containerColor)) { msg ->
                    showToast(context, msg)
                }
            }
            val showProgressBar = viewModel.showProgressbar.collectAsState().value
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                TaskScreen(
                    viewModel,
                    onBackArrowClick = {
                        navController.popBackStack()
                    })
                if (showProgressBar) {
                    ProgressBar()
                }

            }

        }
        composable(
            route = "MyTaskDetails/{taskId}",
            deepLinks = listOf(navDeepLink { uriPattern = "${DeepLink.DEEP_LINK_URL}/{taskId}" }),
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { navBackStackEntry ->

            var details by remember {
                mutableStateOf<TaskAssignedToMe?>(null)
            }
//            //finding task by id ,then passing to the details screen
            LaunchedEffect(Unit) {
                val taskId = navBackStackEntry.arguments?.getString("taskId")
                if (taskId != null) {
                    AssignedToMeTasksObserver.taskDetails(taskId).collect {
                        details = it
                    }
                }
            }
            details?.let { task ->
                TaskDetailsScreen(task = task, onClose = onTaskDetailsClose)
            }
//            taskToOpen?.let { task ->
//                TaskDetailsScreen(task = task, onClose = onTaskDetailsClose)
//            }

        }
        composable(
            route = Screen.MyOwnedTaskDetails.route,
        ) {
            myOwnedTaskToOpen?.let {
                TaskAssignedByMeDetails(it, onClose = {
                    navController.popBackStack()
                })
            }


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