package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.create_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable2
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskToAdd
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
            val myUserId = AuthManager().signedInUserPhone()
            if (myUserId != null) {
                val newUser = FriendShipManager().myFriendList(myUserId)
                    .map {
                        TaskAssignedUser(
                            name = it.name,
                            phone = it.phone,
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

            val myUserId=AuthManager().signedInUserPhone()
            val title = formManager.titleText
            val description = formManager.descriptionText
            val dueDate = formManager.dueDateText
            val assigneesdIds = mutableListOf<String>()
            users.value.forEach {
                if (it.selected) {
                    assigneesdIds += it.phone
                }
            }
            if(myUserId!=null &&assigneesdIds.isNotEmpty()){
               val isSuccess= TaskTable2(myUserId).createTask(
                    TaskToAdd(
                        title=title,
                        description=description,
                        dueDate=dueDate,
                        assignerIdentifier = myUserId,
                        assignee = assigneesdIds
                    )
                )
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