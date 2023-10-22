package com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph

sealed interface Screen {
    val route: String

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

    data object Register : Screen {
        override val route = "Register"
    }

    data object FriendRequest : Screen {
        override val route = "FriendRequest"
    }

    data object Friends : Screen {
        override val route = "Friends"
    }
}