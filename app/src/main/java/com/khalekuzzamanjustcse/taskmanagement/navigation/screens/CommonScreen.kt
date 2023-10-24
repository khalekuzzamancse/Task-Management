package com.khalekuzzamanjustcse.taskmanagement.navigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
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
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.DrawerItemsProvider.drawerGroups
import com.khalekuzzamanjustcse.taskmanagement.ui.components.CircularProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithDrawer(
    drawerState: DrawerState,
    closeDrawer: () -> Unit,
    onDrawerItemClick: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalDrawer(
        modifier = Modifier,
        drawerGroups = drawerGroups,
        onDrawerItemClick = onDrawerItemClick,
        closeDrawer = closeDrawer,
        drawerState = drawerState,
    ) {
        content()
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreen(
    title: String,
    onBackArrowClick: () -> Unit,
    isLoading: Boolean,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackArrowClick) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { scaffoldPadding ->

        if (isLoading) {
            Box(modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressBar()
            }
        } else {
            content(scaffoldPadding)
        }

    }
}