package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.bottom_navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.NavigationItem

@Composable
fun <T> BottomBarDecorator(
    bottomDestinations: List<NavigationItem<T>>,
    onDestinationSelected: (Int) -> Unit,
    selectedDestinationIndex: Int,
    topAppbar:@Composable () -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = topAppbar,
        bottomBar = {
            BottomNavigationBar(
                destinations = bottomDestinations,
                selectedDestinationIndex = selectedDestinationIndex,
                onDestinationSelected = onDestinationSelected
            )
        }
    ) { scaffoldPadding ->
        content(Modifier.padding(scaffoldPadding))
    }
}