package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.modal_drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.NavigationItem
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.modal_drawer.sheet.Sheet

@Composable
fun <T>ModalDrawerDecorator(
    drawerController: ModalDrawerState,
    destinations: List<NavigationItem<T>>,
    selectedDesertionIndex: Int,
    onDestinationSelected: (Int) -> Unit = {},
    content: @Composable () -> Unit,
) {
    ModalDrawer(
        modifier = Modifier,
        drawerState = drawerController.drawerState,
        sheet = {
            Sheet(
                selectedDestinationIndex = selectedDesertionIndex,
                destinations = destinations,
                onDestinationSelected = { index ->
                    onDestinationSelected(index)
                    drawerController.closeDrawer()
                }
            )
        },
        content=content
    )
}


@Composable
fun <T>ModalDrawerDecorator(
    destinations: List<NavigationItem<T>>,
    selectedDesertionIndex: Int,
    onDestinationSelected: (Int) -> Unit = {},
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val drawerController by remember {
        mutableStateOf(ModalDrawerState(scope))
    }
    ModalDrawer(
        modifier = Modifier,
        drawerState = drawerController.drawerState,
        sheet = {
            Sheet(
                selectedDestinationIndex = selectedDesertionIndex,
                destinations = destinations,
                onDestinationSelected = { index ->
                    onDestinationSelected(index)
                    drawerController.closeDrawer()
                }
            )
        },
        content=content
    )
}

