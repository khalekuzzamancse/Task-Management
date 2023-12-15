package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCRUD
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedUser
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateTaskViewModel(
    val formManager: CreateTaskFormManager,
   val  onShowToast:(message:String)->Unit,
) : ViewModel() {

    private val _userSelectedMode = MutableStateFlow(false)
    val userSelectedMode = _userSelectedMode.asStateFlow()
    fun onUserSelectedModeChanged(show: Boolean) {
        _userSelectedMode.value = show
    }

    private val _showProgressbar = MutableStateFlow(false)
    val showProgressbar = _showProgressbar.asStateFlow()

    //Manipulating the selected user
    //---------------
    private val _users = MutableStateFlow(emptyList<TaskAssignedUser>())
    val users = _users.asStateFlow()

    init {
        viewModelScope.launch {
            val myUserId = AuthManager.signedInUserPhone()
            if (myUserId != null) {
                val newUser = FriendShipObserver.myFriends.value
                    .map {
                        TaskAssignedUser(
                            name = it.friendName,
                            phone = it.friendPhone,
                            selected = false
                        )
                    }
                withContext(Dispatchers.Main) {
                    _users.value = newUser
                }
            }

        }
    }


    fun onDone() {
        _showProgressbar.value=true
        viewModelScope.launch {

            val myUserId=AuthManager.signedInUserPhone()
            val title = formManager.titleText
            val description = formManager.descriptionText
            val dueDate = formManager.dueDateText
            val assignedUsers = mutableListOf<AssignedUser>()
            users.value.forEach {
                if (it.selected) {
                    assignedUsers += AssignedUser(
                        userId =it.phone,
                        state = 1
                    )
                }
            }
            if(myUserId!=null &&assignedUsers.isNotEmpty()){
               val task= Task(
                   title=title,
                   description=description,
                   dueTime =dueDate,
                   assignerId = myUserId,
                   assignedUsers = assignedUsers
               )
               val isSuccess= DatabaseCRUD().create(collectionName = "NewTaks", task)
                onShowToast(
                    if(isSuccess)"Created successfully " else "Failed to create"
                )
            }
            withContext(Dispatchers.Main){
                _showProgressbar.value=false
            }


        }


    }


    fun onLongClick(index: Int) {
        val user = _users.value[index]
        val tmp = _users.value.map { it }.toMutableList()
        tmp.removeAt(index)
        tmp.add(user.copy(selected = !user.selected))
        _users.value = tmp
    }


}