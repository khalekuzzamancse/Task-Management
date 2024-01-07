package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationRailState(
    val options: List<NavigationItem<Int>>,
    val selectedItem: Int,
    val isExpandMode: Boolean
)
data class NavigationGroup(
    val name: String,
    val icon: ImageVector,
    val members: List<NavigationItem<Int>>
)

//use key to uniquely identify
data class NavigationItem<T>(
    val key:T,
    val label: String,
    val unFocusedIcon: ImageVector,
    val focusedIcon: ImageVector =unFocusedIcon,
    val route: String = label,
    val badge: String? = null,
)