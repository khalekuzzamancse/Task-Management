package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.task_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data.UserCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskScreenViewModel : ViewModel() {
    //Manipulating the user selection and task
    //---------------
    private val _userSelectedMode = MutableStateFlow(false)
    val userSelectedMode = _userSelectedMode.asStateFlow()
    fun onUserSelectedModeChanged(show: Boolean) {
        _userSelectedMode.value = show
    }

    init {
        viewModelScope.launch {
                val newUser=UserCollections()
                    .allUsers()
                    .map {
                        TaskAssignedUser(
                            name = it.name,
                            phone = it.phone,
                            selected = false
                        )
                    }
            withContext(Dispatchers.Main){
                _users.value=newUser
            }
        }
    }

    //Manipulating the title input of tasks
    //---------------
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    fun onTitleChanged(title: String) {
        _title.value = title
    }

    //Manipulating the description input
    //---------------
    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()
    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    //Manipulating the selected user
    //---------------
    private val _users = MutableStateFlow(
        listOf(
            TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813861", selected = false),
            TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813862", selected = false),
            TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813863", selected = false),
            TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813864", selected = false),
        )
    )
    val users = _users.asStateFlow()
    fun onLongClick(index: Int) {
        val user = _users.value[index]
        val tmp = _users.value.map { it }.toMutableList()
        tmp.removeAt(index)
        tmp.add(user.copy(selected = !user.selected))
        _users.value = tmp
    }


}