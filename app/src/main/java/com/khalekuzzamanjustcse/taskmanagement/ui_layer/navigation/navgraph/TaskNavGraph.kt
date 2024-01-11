package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.khalekuzzamanjustcse.taskmanagement.DeepLink
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedByMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedToMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskAssignedToMe
import com.khalekuzzamanjustcse.taskmanagement._2d_graph._2dGraph
import com.khalekuzzamanjustcse.taskmanagement.bar_chart.BarChart
import com.khalekuzzamanjustcse.taskmanagement.calender_view._2dCalender
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.BottomNavigationItem
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen.TopNBottomBarDecorator
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.MyTaskViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskAssignedByMeDetails1
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskByMeList
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskOwnedByMe
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskToMeDetailsScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TasksAssignedToMeList
import kotlinx.coroutines.launch

private val taskDestinations = listOf(
    BottomNavigationItem(
        label = "ToDo",
        selectedIcon = Icons.Filled.Task,
        unselectedIcon = Icons.Outlined.Task,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "By Me",
        selectedIcon = Icons.AutoMirrored.Filled.ListAlt,
        unselectedIcon = Icons.AutoMirrored.Outlined.ListAlt,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "History",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "BarChart",
        selectedIcon = Icons.Filled.BarChart,
        unselectedIcon = Icons.Outlined.BarChart,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "Calendar",
        selectedIcon = Icons.Filled.CalendarMonth,
        unselectedIcon = Icons.Outlined.CalendarMonth,
        hasNews = false,
    ),


    )


@Preview
@Composable
fun TopBottomBarDecoratorPreview() {
    TaskNavGraph()
}


@Composable
fun TaskNavGraph() {
    val navController = rememberNavController()
    var taskDetailsOpen by remember {
        mutableStateOf(false)
    }

    val onNavigationRequest: (Screen) -> Unit = {
        navController.navigate(it.route)
        if (it == Screen.MyOwnedTaskDetails || it == Screen.TaskByMeDetails) {
            taskDetailsOpen = true
        }

    }
    var bottomNavItemSelectedItemIndex by remember {
        mutableIntStateOf(0)

    }
    var topNavigationEnabled by remember {
        mutableStateOf(true)
    }


    val onItemSelected: (Int) -> Unit = { index ->
        bottomNavItemSelectedItemIndex = index
        if (taskDetailsOpen)
            navController.popBackStack()
        taskDetailsOpen = false
        // Now you can use previousBottomNavItemSelectedItemIndex to access the previous index
        when (index) {
            0 -> {
                onNavigationRequest(Screen.TasksByMe)
                topNavigationEnabled = true
            }
            1 -> {
                onNavigationRequest(Screen.TasksToMe)
                topNavigationEnabled = false
            }

            2 -> {
                onNavigationRequest(Screen.TaskHistory)
                topNavigationEnabled = false
            }
            3 -> {
                onNavigationRequest(Screen.TaskBarChar)
                topNavigationEnabled = false
            }
            4 -> {
                onNavigationRequest(Screen.CalenderView)
                topNavigationEnabled = false
            }
        }
    }

    TopNBottomBarDecorator(
        screenTitle = "Tasks",
        topBarNavIcon = if(topNavigationEnabled)Icons.AutoMirrored.Filled.ArrowBack else null,
        onTopBarNavIconClicked = {
            navController.popBackStack()
        },
        bottomNavigationItems = taskDestinations,
        bottomNavItemSelectedItemIndex = bottomNavItemSelectedItemIndex,
        onBottomNavItemSelected = onItemSelected
    ) {
        ContentSection(
            modifier = it,
            navController = navController,
            onNavigationRequest = {
                navController.navigate(it)
                topNavigationEnabled=true
            }
        )
    }

}


@Composable
private fun ContentSection(
    modifier: Modifier,
    onNavigationRequest: (String) -> Unit,
    navController: NavHostController,
) {
    val scope = rememberCoroutineScope()
    var myOwnedTaskToOpen: TaskOwnedByMe? by remember { mutableStateOf(null) }
    val onMyOwnedTaskOpenDetail: (TaskOwnedByMe) -> Unit = { task ->
        scope.launch {
            AssignedByMeTasksObserver.taskDetails(task.taskId).collect {
                myOwnedTaskToOpen = it
            }
        }
        onNavigationRequest(Screen.MyOwnedTaskDetails.route)
        //navController.navigate(S.route)
    }

    val onTaskDetailsOpen: (TaskAssignedToMe) -> Unit = { task ->
        onNavigationRequest("MyTaskDetails/${task.taskId}")
        //  navController.navigate("MyTaskDetails/${task.taskId}")
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.TasksByMe.route
    ) {

        composable(
            route = Screen.TasksByMe.route
        ) {
            TaskByMeList(
                tasks = AssignedByMeTasksObserver._taskOwnedByMe.collectAsState().value,
                onDetailsRequest = onMyOwnedTaskOpenDetail
            )

        }
        composable(
            route = Screen.TasksToMe.route
        ) {
            val viewModel = remember {
                MyTaskViewModel()
            }
            TasksAssignedToMeList(
                tasks = AssignedToMeTasksObserver.taskToMe.collectAsState().value,
                onTaskCompleteRequest = viewModel::onCheckChanged,
                onTaskDetailsRequest = onTaskDetailsOpen
            )
        }
        composable(
            route = Screen.MyOwnedTaskDetails.route,
        ) {
            myOwnedTaskToOpen?.let {
                TaskAssignedByMeDetails1(task = it)
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
            LaunchedEffect(Unit) {
                val taskId = navBackStackEntry.arguments?.getString("taskId")
                if (taskId != null) {
                    AssignedToMeTasksObserver.taskDetails(taskId).collect {
                        details = it
                    }
                }
            }
            details?.let { task ->
                TaskToMeDetailsScreen(task = task)
            }

        }
        composable(Screen.TaskHistory.route){
            _2dGraph()
        }
        composable(Screen.TaskBarChar.route){
            BarChart()
        }
        composable(Screen.CalenderView.route){
            _2dCalender()
        }
    }
}
