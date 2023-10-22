package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


data class User(
    val name: String,
    val phone: String,
    val isFriend: Boolean = false,
    val isSendRequest: Boolean = false
)

@Preview
@Composable
fun UserListScreenPreview() {
    UserListScreen(
        listOf(
            User("Mr Bean A", "000000000"),
            User("Mr Bean B", "11111111"),
            User("Mr Bean C", "22222222", isFriend=true),
            User("Mr Bean D", "33333333333", isFriend = false, isSendRequest = true),
        )
    ) {

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    contacts: List<User>,
    onNavIconClicked: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") },
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
    ) { innerPaddingPadding ->
        Column(

            modifier = Modifier
                .padding(innerPaddingPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(items = contacts) { contact ->
                    UserInfoCard(name =contact.name, phone = contact.phone ) {
                        if (!contact.isFriend) {
                            val icon=if(contact.isSendRequest) {Icons.Filled.PersonRemove }else {Icons.Filled.PersonAdd}
                            IconButton(
                                onClick = {},
                            ) {
                                Icon(imageVector = icon, contentDescription = null)
                            }
                        }
                    }

                }
            }


        }

    }

}
