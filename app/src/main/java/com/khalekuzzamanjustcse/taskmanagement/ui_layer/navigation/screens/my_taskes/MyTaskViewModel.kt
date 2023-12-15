package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskAssignedToMe
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskUpdater
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyTaskViewModel : ViewModel() {
    //Handling tasks
    private val _tasks = MutableStateFlow(emptyList<TaskAssignedToMe>())
    val tasks = _tasks.asStateFlow()
    private val _currentOpenTask = MutableStateFlow<TaskAssignedToMe?>(null)
    val currentOpenTask = _currentOpenTask.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    fun onLongClick(task: TaskAssignedToMe) {
        _currentOpenTask.value = task
    }

    fun onTaskDescriptionClose() {
        _currentOpenTask.value = null
    }

    fun onCheckChanged(task: TaskAssignedToMe, checked: Boolean) {

        viewModelScope.launch {
            val myUserId = AuthManager.signedInUserPhone()
            myUserId?.let {
               TaskUpdater().markAsComplete(
                    myUserId = myUserId,
                    taskId = task.taskId
                )
            }

        }

    }


}