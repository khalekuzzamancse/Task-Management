package com.khalekuzzanman.just.cse.friend.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericListScreen(
    items: List<T>,
    screenTitle: String,
    onNavIconClicked: () -> Unit,
    navIcon: ImageVector,
    itemContent: @Composable (T) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavIconClicked) {
                        Icon(imageVector = navIcon, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { innerPadding ->
        GenericListScreen(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            items = items,
            itemContent = itemContent
        )

    }
}


@Composable
fun <T> GenericListScreen(
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
