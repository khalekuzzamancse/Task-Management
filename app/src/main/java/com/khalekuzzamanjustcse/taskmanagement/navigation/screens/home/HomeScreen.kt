package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.home

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Segment
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes.MyTaskListScreen
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes.MyTaskViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTaskCard
import com.khalekuzzamanjustcse.taskmanagement.ui.components.ProfileImage
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme


@Preview
@Composable
fun HomePreview() {
    TaskManagementTheme {
        Home()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    Scaffold(
        topBar = {
            HomeTopAppbar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
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
            UserInfo()
            Spacer(modifier = Modifier.height(16.dp))
            MyTasks()

        }
    }


}

@Composable
fun MyTasks() {
    val viewModel = remember {
        MyTaskViewModel()
    }
    val items = viewModel.tasks.collectAsState().value
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            items(items) { myTask ->
                MyTaskCard(
                    title = myTask.title,
                    assigner = myTask.assignerName,
                    onCheckedChanged = {
                        viewModel.onCheckChanged(myTask, it)
                    },
                    checked = myTask.complete,
                    onLongClick = {
                        viewModel.onLongClick(myTask)
                    }
                )
            }
        }
    }
}

@Composable
fun UserInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Hello , John !",
            style = MaterialTheme.typography.displaySmall,
        )
        Text(
            text = "Have a nice day!",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppbar() {
    TopAppBar(
        title = {
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Segment, null)
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


        val email = AuthManager().singedInUserEmail()

        Text("Email: ${email.toString()}")
        Text("Phone Number: $phone")

    }
}
