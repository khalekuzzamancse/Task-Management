package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            Log.d("taskAsignee:", "${task.assigneePhone}")
//            TaskTable().updateTask(task.copy(complete = checked))
        }

    }

    private var _taskOwnedByMe = MutableStateFlow(emptyList<TaskOwnedByMe>())
    val taskOwnedByMe = _taskOwnedByMe.asStateFlow()

    init {
        viewModelScope.launch {
            val myUserId = AuthManager().signedInUserPhone()
            if (myUserId != null) {
                _taskOwnedByMe.value = TaskTable2(myUserId).taskOwnedByMe()
            }

        }
    }
    init {

        viewModelScope.launch {
            val myUserId = AuthManager().signedInUserPhone()
            if (myUserId != null) {
                val taskCollection = TaskTable2(myUserId)
                withContext(Dispatchers.Main) {
                    _tasks.value+= taskCollection.getAssignedTasks(myUserId).map {
                        TaskEntity(
                            title = it.title,
                            description = it.description,
                            assignerName = it.assignerName,
                            assigneePhone = it.assignerPhone,
                            complete = it.assignerPhone==myUserId
                        )
                    }

                    _isLoading.value = false

                }

            }



        }
    }


}