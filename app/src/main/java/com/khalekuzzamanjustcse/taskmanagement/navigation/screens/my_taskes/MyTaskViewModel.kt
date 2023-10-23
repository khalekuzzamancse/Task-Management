package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes

import androidx.lifecycle.ViewModel
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyTaskViewModel : ViewModel() {
    //Handling tasks
    private val _tasks = MutableStateFlow(dummyTaskList)
    val tasks = _tasks.asStateFlow()
    private val _currentOpenTask = MutableStateFlow<MyTask?>(null)
    val currentOpenTask=_currentOpenTask.asStateFlow()
    fun onLongClick(task: MyTask) {
        _currentOpenTask.value = task
    }

    fun onTaskDescriptionClose() {
        _currentOpenTask.value = null
    }

    fun onCheckChanged(task: MyTask, checked: Boolean) {
        _tasks.update { tasks ->
            tasks.map { if (it.tile == task.tile) it.copy(status = checked) else it }
        }
    }


}