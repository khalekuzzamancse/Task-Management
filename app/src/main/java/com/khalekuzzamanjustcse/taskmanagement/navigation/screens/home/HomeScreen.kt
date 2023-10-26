package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Segment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data.FirebaseFireStore
import com.khalekuzzamanjustcse.taskmanagement.data.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes.MyTaskViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui.components.ProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme


@Preview
@Composable
fun HomePreview() {
    TaskManagementTheme {
        Home(onCreateTask = {})
    }
}

@Composable
fun Home(
    onCreateTask: () -> Unit = {},
    openDrawer: () -> Unit = {},
    onTaskDetailsOpen: (TaskEntity) -> Unit = {},
) {

    var username by remember {
        mutableStateOf("NULL")
    }
    val viewModel = remember {
        MyTaskViewModel()
    }
    val onNavigationIconClick: () -> Unit = {
        openDrawer()
    }
    LaunchedEffect(Unit) {
        val user = FirebaseFireStore().getSingedUser(AuthManager().singedInUserEmail())
        user?.let {
            username = it.name
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppbar(
                onNavigationIconClick = onNavigationIconClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateTask) {
                Icon(Icons.Filled.Add, null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp)
                .fillMaxSize()
        ) {
            UserInfo(username = username)
            Spacer(modifier = Modifier.height(16.dp))
            MyTaskList(
                viewModel, onTaskClick = { myTask ->
                    viewModel.onLongClick(myTask)
                    onTaskDetailsOpen(myTask)
                })


        }


    }


}


@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    username: String = "",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Hello , $username !",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Have a nice day!",
            style = MaterialTheme.typography.labelLarge,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppbar(
    navigationIcon: ImageVector = Icons.Filled.Segment,
    onNavigationIconClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(navigationIcon, null)
            }
        },
        actions = {
            Spacer(modifier = Modifier.weight(1f))
            ProfileImage()
        },
    )
}

@Composable
fun HomePage() {
    var phone by remember {
        mutableStateOf("NULL")
    }
    LaunchedEffect(Unit) {
        phone = AuthManager().signedInUserPhone().toString()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


    }
}
