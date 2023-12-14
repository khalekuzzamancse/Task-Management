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

    data object Users : Screen() {
        override val route = "Users"
    }


    data object FriendRequest : Screen() {
        override val route = "FriendRequest"
    }

    data object Friends : Screen() {
        override val route = "Friends"
    }
    data object Task : Screen() {
        override val route = "Task"
    }
    data object MyTask : Screen() {
        override val route = "MyTask"
    }

    data object MyTaskDetails : Screen() {
        override val route = "MyTaskDetails"
    }
    data object MyOwnedTaskDetails : Screen() {
        override val route = "MyOwnedTaskDetails"
    }
}