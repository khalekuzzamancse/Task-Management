package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.device_contact

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.data.UserCollections
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceContactViewModel(
    val context: Context,
) : ViewModel(){
    private val _users = MutableStateFlow<List<Contact>>(emptyList())
    val users = _users.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch {
            val newUsers =FetchContact(context).getContact()
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