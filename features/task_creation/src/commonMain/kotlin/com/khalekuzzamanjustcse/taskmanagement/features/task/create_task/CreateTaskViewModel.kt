package com.khalekuzzamanjustcse.taskmanagement.features.task.create_task

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
val taskAssignedUserList: List<TaskAssignedUser> = listOf(
    TaskAssignedUser("John Doe", "123-456-7890", false),
    TaskAssignedUser("Jane Smith", "987-654-3210", false),
    TaskAssignedUser("Bob Johnson", "555-123-4567", false),
    TaskAssignedUser("Alice Williams", "888-999-0000", false)
)


class CreateTaskViewModel(
    val onShowToast: (message: String) -> Unit,
    val scope: CoroutineScope) {
    companion object {
      private  const val ENABLE_USER_SELECTION_MODE = true
       private const val DISABLE_USER_SELECTION_MODE = false
    }

    private val _userSelectedMode = MutableStateFlow(false)
    val userSelectedMode = _userSelectedMode.asStateFlow()

    private val _showProgressbar = MutableStateFlow(false)
    val showProgressbar = _showProgressbar.asStateFlow()

    //Manipulating the selected user
    //---------------
    private val _users = MutableStateFlow(taskAssignedUserList.sortedBy { it.name })
    val users = _users.asStateFlow()
    private fun updateUsers(users:List<TaskAssignedUser>){
        _users.value=users.sortedBy { it.name }
    }
    val formState=CreateTaskFormState()

    //Events

    fun onCreateTaskRequest() {
        _userSelectedMode.value = ENABLE_USER_SELECTION_MODE
    }

    fun onCreateTaskConfirm() {
        _userSelectedMode.value = DISABLE_USER_SELECTION_MODE
        println("${formState.getCreatedTaskInfo()}+\n+${getSelectedUsers()}")
    }
    fun onCreateTaskCancel() {
        _userSelectedMode.value = DISABLE_USER_SELECTION_MODE
    }

    fun onUserChoose(index: Int) {
        val user = _users.value[index]
        val tmp = _users.value.map { it }.toMutableList()
        tmp.removeAt(index)
        tmp.add(user.copy(selected = !user.selected))
        updateUsers(tmp)
    }
    fun getSelectedUsers():List<TaskAssignedUser>{
        return _users.value.filter { it.selected }

    }


}