package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyTaskViewModel : ViewModel() {
    //Handling tasks
    private val _tasks = MutableStateFlow(dummyTaskList)
    val tasks = _tasks.asStateFlow()
    private val _currentOpenTask = MutableStateFlow<MyTask?>(null)
    val currentOpenTask=_currentOpenTask.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
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
    init {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch {
            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startTime
            if (elapsedTime < 2000)
                delay(1500)
            withContext(Dispatchers.Main) {
                _isLoading.value = false
            }
        }
    }


}