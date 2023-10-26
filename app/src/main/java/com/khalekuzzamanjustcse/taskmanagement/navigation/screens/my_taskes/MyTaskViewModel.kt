package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.createNotification
import com.khalekuzzamanjustcse.taskmanagement.data.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.data.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.data.TaskTable
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyTaskViewModel : ViewModel() {
    //Handling tasks
    private val _tasks = MutableStateFlow(emptyList<TaskEntity>())
    val tasks = _tasks.asStateFlow()
    private val _currentOpenTask = MutableStateFlow<TaskEntity?>(null)
    val currentOpenTask = _currentOpenTask.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    fun onLongClick(task: TaskEntity) {
        _currentOpenTask.value = task
    }

    fun onTaskDescriptionClose() {
        _currentOpenTask.value = null
    }

    fun onCheckChanged(task: TaskEntity, checked: Boolean) {
        viewModelScope.launch {
            TaskTable().updateTask(task.copy(complete = checked))
        }

    }


    init {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch {
            TaskTable().getTasks().collect { newTaskes ->
                val endTime = System.currentTimeMillis()
                val elapsedTime = endTime - startTime
                if (elapsedTime < 2000)
                    delay(1000)
                withContext(Dispatchers.Main) {
                    Log.i("TaskTable: ", newTaskes.toString())
                    _tasks.value = newTaskes
                    _isLoading.value = false


                }
            }


        }
    }


}