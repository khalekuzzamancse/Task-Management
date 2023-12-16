package com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.BottomNavBar
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.BottomNavigationItem
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.items
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.navgraph.Screen


@Preview
@Composable
fun GenericScreenPreview() {
    GenericScreen(
        screenTitle = "Demo",
        topBarNavIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onTopBarNavIconClicked = {},
        bottomNavigationItems = items,
        bottomNavItemSelectedItemIndex = 0,
        onBottomNavItemSelected = {}
    ) {
        Text(modifier = it, text = "Place the Nav host here..")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericScreen(
    screenTitle: String,
    topBarNavIcon: ImageVector?,
    onTopBarNavIconClicked: () -> Unit,
    bottomNavigationItems: List<BottomNavigationItem>,
    bottomNavItemSelectedItemIndex: Int,
    onBottomNavItemSelected: (Int) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    if (topBarNavIcon != null) {
                        IconButton(onClick = onTopBarNavIconClicked) {
                            Icon(imageVector = topBarNavIcon, contentDescription = null)
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItemIndex = bottomNavItemSelectedItemIndex,
                onItemSelected = onBottomNavItemSelected,
                items = bottomNavigationItems
            )
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericListScreen1(
    listItems: List<T>,
    screenTitle: String,
    onNavIconClicked: () -> Unit,
    topBarNavIcon: ImageVector,
    itemContent: @Composable (T) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavIconClicked) {
                        Icon(imageVector = topBarNavIcon, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { innerPadding ->
        GenericListComposable(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            items = listItems,
            itemContent = itemContent
        )

    }
}

@Composable
fun <T> GenericListComposable(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemContent: @Composable (T) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(items) { item ->
                itemContent(item)
            }
        }
    }
}