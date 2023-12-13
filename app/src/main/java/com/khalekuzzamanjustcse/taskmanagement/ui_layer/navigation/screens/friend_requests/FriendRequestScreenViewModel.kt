package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendRequestScreenViewModel : ViewModel() {
  private val manager=  FriendShipManager()
    fun acceptFriendRequest(senderPhoneNo: String) {
        viewModelScope.launch {
            manager.acceptRequest(senderPhoneNo,"2")
        }
    }

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val singedUserPhone=AuthManager().signedInUserPhone()
            if(singedUserPhone!=null) {
                val request = FriendShipManager().getFriendRequest(singedUserPhone)
                val newUsers = request.map {User(
                    name = it.name,
                    phone = it.phone
                ) }
                withContext(Dispatchers.Main) {
                    _users.value = newUsers
                    _isLoading.value = false
                }
            }


        }
    }
}