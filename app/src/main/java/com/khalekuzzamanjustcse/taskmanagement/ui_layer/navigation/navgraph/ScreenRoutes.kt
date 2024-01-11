package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph

sealed  class Screen {
    open val route: String=""
    open val parameterNames:List<String> = emptyList()

    data object Home : Screen() {
        override val route = "Home"
    }
    data object Login : Screen() {
        override val route = "Login"
    }

    data object Register : Screen() {
        override val route = "Register"
    }
    data object AuthGraph : Screen() {
        override val route = "AuthGraph"
    }

    data object Contact : Screen() {
        override val route = "Contact"
    }

    data object Connections : Screen() {
        override val route = "Connections"
    }


    data object FriendRequest : Screen() {
        override val route = "FriendRequest"
    }

    data object Friends : Screen() {
        override val route = "Friends"
    }
    data object TaskCreate : Screen() {
        override val route = "Task"
    }
    data object TasksByMe : Screen() {
        override val route = "TasksByMe"
    }
    data object TasksToMe : Screen() {
        override val route = "TasksToMe"
    }
    data object ToDO : Screen() {
        override val route = "ToDO"
    }

    data object TaskByMeDetails : Screen() {
        override val route = "TaskByMeDetails"
    }

    data object MyOwnedTaskDetails : Screen() {
        override val route = "MyOwnedTaskDetails"
    }
    data object TaskHistory : Screen() {
        override val route = "TaskHistory"
    }
    data object TaskBarChar : Screen() {
        override val route = "TaskBarChar"
    }
    data object CalenderView : Screen() {
        override val route = "CalenderView"
    }
}