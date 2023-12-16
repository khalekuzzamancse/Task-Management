package com.khalekuzzamanjustcse.taskmanagement.ui_layer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.BottomNavBar
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.BottomNavigationItem
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.bottom_navigation.items
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.CommonScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.Contact

@Preview
@Composable
fun GenericListScreenPreview() {
    val contactList = listOf(
        Contact("John Doe", "123-456-7890"),
        Contact("Jane Smith", "987-654-3210"),
        Contact("John Doe", "123-456-7890"),
        Contact("Jane Smith", "987-654-3210"),
        Contact("John Doe", "123-456-7890"),
        Contact("Jane Smith", "987-654-3210"),
        Contact("John Doe", "123-456-7890"),
        Contact("Jane Smith", "987-654-3210"),
    )
    var bottomNavItemSelectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    val onItemSelected: (Int) -> Unit = {
        bottomNavItemSelectedItemIndex = it
    }

    GenericListScreen(
        listItems = contactList,
        listItemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone, savedInContact = false)
        },
        screenTitle = "Contacts",
        onNavIconClicked = { },
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        bottomNavigationItems = items,
        onBottomNavItemSelected = onItemSelected,
        bottomNavItemSelectedItemIndex=bottomNavItemSelectedItemIndex

    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericListScreen(
    listItems: List<T>,
    screenTitle: String,
    navigationIcon:ImageVector,
    onNavIconClicked: () -> Unit,
    bottomNavigationItems:List<BottomNavigationItem>,
    bottomNavItemSelectedItemIndex: Int,
    onBottomNavItemSelected: (Int) -> Unit,
    listItemContent: @Composable (T) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavIconClicked) {
                        Icon(imageVector = navigationIcon, contentDescription = null)
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(listItems) { item ->
                    listItemContent(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericListScreen(
    items: List<T>,
    screenTitle: String,
    onNavIconClicked: () -> Unit,
    itemContent: @Composable (T) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavIconClicked) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(items) { item ->
                    itemContent(item)
                }
            }
        }
    }
}

@Composable
fun <T> GenericListScreen(
    modifier: Modifier = Modifier,
    items: List<T>,
    screenTitle: String,
    isLoading: Boolean ,
    onBack: () -> Unit,
    showSnackBar: Boolean =false,
    snackBarMessage: String ="",
    itemContent: @Composable (T) -> Unit,
) {
    CommonScreen(
        title = screenTitle,
        onBackArrowClick = onBack,
        isLoading = isLoading,
        showSnackBar=showSnackBar,
        snackBarMessage = snackBarMessage
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(items) { item ->
                    itemContent(item)
                }
            }
        }
    }
}
