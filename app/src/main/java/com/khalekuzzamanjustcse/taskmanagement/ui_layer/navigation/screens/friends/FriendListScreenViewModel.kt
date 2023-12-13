package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipManager
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendListScreenViewModel : ViewModel(){
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val singedUserPhone= AuthManager().signedInUserPhone()
            if(singedUserPhone!=null) {
                val newUsers =FriendShipManager().myFriendList(singedUserPhone)
                withContext(Dispatchers.Main) {
                    _users.value = newUsers.map { User(
                        name = it.name,
                        phone = it.phone,
                        email = "",
                        isFriend = true,
                    ) }
                    _isLoading.value = false
                }
            }
            }

        }
    }
