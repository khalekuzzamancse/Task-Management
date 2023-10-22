package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendRequestListScreen(
    contacts: List<User>,
    onNavIconClicked: () -> Unit,
) {
    LaunchedEffect(Unit){

        FriendManager().getFriendRequest()
    }
    LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Friends Request") },
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(items = contacts) { contact ->

                    UserInfoCard(name =contact.name, phone = contact.phone ){
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = null)
                        }
                    }

                }
            }


        }

    }

}
