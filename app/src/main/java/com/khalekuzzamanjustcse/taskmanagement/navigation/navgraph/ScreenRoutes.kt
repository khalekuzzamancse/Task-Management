package com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph

sealed interface Screen {
    val route: String

    data object Home : Screen {
        override val route = "Home"
    }
    data object Login : Screen {
        override val route = "Login"
    }

    data object Logout : Screen {
        override val route = "Logout"
    }

    data object Contact : Screen {
        override val route = "Contact"
    }

    data object Users : Screen {
        override val route = "Users"
    }


    data object FriendRequest : Screen {
        override val route = "FriendRequest"
    }

    data object Friends : Screen {
        override val route = "Friends"
    }
    data object Task : Screen {
        override val route = "Task"
    }
    data object MyTask : Screen {
        override val route = "MyTask"
    }

}