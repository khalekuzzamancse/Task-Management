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
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data.FriendManager



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendRequestListScreen(
    contacts: List<User>,
    onNavIconClicked: () -> Unit,
    onAcceptButtonClick: (phone: String) -> Unit,
) {
    LaunchedEffect(Unit){

        FriendManager().getFriendRequest()
    }
    val context = LocalContext.current
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
                    FriendRequestCard(contact) {
                        onAcceptButtonClick(contact.phone)
                    }
                }
            }


        }

    }

}

@Composable
fun FriendRequestCard(
    contact: User,
    onAcceptButtonClick: () -> Unit,
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
            IconButton(
                onClick = onAcceptButtonClick,
            ) {
                Icon(imageVector = Icons.Filled.DoneOutline, contentDescription = null)
            }
        }

    }

}