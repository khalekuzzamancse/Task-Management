package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.CircularProgressBar
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.DrawerItemsProvider.drawerGroups
import kotlinx.coroutines.launch


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
    showSnackBar: Boolean = false,
    snackBarMessage: String = "",
    content: @Composable (PaddingValues) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val show:()->Unit ={
        scope.launch {
            hostState.showSnackbar(snackBarMessage)
        }
    }
    Log.i("SnackbarMessage:","$showSnackBar")



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackArrowClick) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },

            )
        }
    ) { scaffoldPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
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
        if (showSnackBar) {
            show()
        }
    }
}