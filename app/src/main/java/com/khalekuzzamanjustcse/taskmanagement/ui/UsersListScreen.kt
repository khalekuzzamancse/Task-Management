package com.khalekuzzamanjustcse.taskmanagement.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.Contact


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
            User("Abul", "000000000"),
            User("Babul", "11111111"),
            User("Cabul", "22222222", true),
            User("Dabul", "33333333333", false, true),
        ),
        onNavIconClicked = {},
        onAddFriendIconClick = {
            Log.i("FriendRequestTo: ", it)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    contacts: List<User>,
    onNavIconClicked: () -> Unit,
    onAddFriendIconClick: (phone: String) -> Unit,
) {
    val context = LocalContext.current
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
    ) { innterPadding ->
        Column(
            modifier = Modifier
                .padding(innterPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(items = contacts) { contact ->
                    UserCard(contact) {
                        onAddFriendIconClick(contact.phone)
                    }
                }
            }


        }

    }

}

@Composable
fun UserCard(
    contact: User,
    onAddFriendIconClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Contact Icon",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = contact.name, fontWeight = FontWeight.Bold)
                Text(text = contact.phone, color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f)) // U
            if (!contact.isFriend) {
              val icon=if(contact.isSendRequest) Icons.Filled.PersonRemove else Icons.Filled.PersonAdd
                IconButton(
                    onClick = onAddFriendIconClick,
                ) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        }

    }

}