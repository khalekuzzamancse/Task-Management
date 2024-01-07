package com.khalekuzzamanjustcse.taskmanagement.features.common_ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.bottom_navigation.BottomNavigationBar
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.NavigationItem

@Composable
fun <T> TopNBottomBarDecorator(
    topBarTitle: String,
    topNavigationIcon: ImageVector? = null,
    onNavigationIconClick: () -> Unit={},
    bottomDestinations: List<NavigationItem<T>>,
    onDestinationSelected: (Int) -> Unit,
    selectedDestinationIndex: Int,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppbar(
                title = topBarTitle,
                navigationIcon = topNavigationIcon,
                onNavigationIconClick = onNavigationIconClick
            )
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppbar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(
                    onClick = onNavigationIconClick
                ) {
                    Icon(
                        navigationIcon, null
                    )
                }
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
        )

    )

}