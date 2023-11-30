package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendRequestScreenViewModel : ViewModel(){
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch {
            val request = FriendManager().getFriendRequest()
            val newUsers =request.map { it.user }
            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startTime
            if (elapsedTime < 2000)
                delay(1500)
            withContext(Dispatchers.Main) {
                _users.value = newUsers
                _isLoading.value = false
            }
        }
    }
}