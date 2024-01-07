package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.modal_drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ModalDrawerState(
    private val scope: CoroutineScope,
) {
    val drawerState = DrawerState(DrawerValue.Closed)

    fun openDrawer() {
        scope.launch {
            drawerState.open()
        }
    }

    fun closeDrawer() {
        scope.launch {
            drawerState.close()
        }
    }
}
