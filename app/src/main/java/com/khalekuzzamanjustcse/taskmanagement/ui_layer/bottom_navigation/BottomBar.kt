package com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


data class BottomNavigationItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector? = null,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val items = listOf(
    BottomNavigationItem(
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),
    BottomNavigationItem(
        label = "Chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        label = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        hasNews = true,
    ),
)

@Preview
@Composable
fun BottomPrev() {
    var selected by remember {
        mutableIntStateOf(0)
    }
    val onItemSelected: (Int) -> Unit = {
        selected = it
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItemIndex = selected,
                onItemSelected = onItemSelected,
                items = items
            )
        }

    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {

        }
    }

}

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    items:List<BottomNavigationItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    NavigationBar(modifier = modifier) {
       items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                label = {
                    Text(text = item.label)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        (if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon)?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.label
                            )
                        }
                    }
                }
            )
        }
    }

}
