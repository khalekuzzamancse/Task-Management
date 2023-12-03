package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceContactViewModel(
    private var context: Context,
) : ViewModel() {

    companion object {
        private const val TAG = "DeviceContactViewModelLog: "
    }

    private val _users = MutableStateFlow<List<Contact>>(emptyList())
    val users = _users.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()


    //Snack bar management
    var snackBarMessage = "Loaded successfully"
        private set
    private val _showSnackBar = MutableStateFlow(false)
    val showSnackBar = _showSnackBar.asStateFlow()
    //

    init {
        viewModelScope.launch {
            delay(1500)//show progress bar 1.5 seconds
            fetchUsers()
            _isLoading.value = false
            _showSnackBar.value = true
        }
    }

    private suspend fun fetchUsers() {
        val updatedLocalContacts = FetchContact(context).getContact()
        val users = UserCollections().allUsers()

        updatedLocalContacts.forEach { localContact ->
            var savedContact = localContact.phone.replace("[+\\s-]".toRegex(), "")
            val numberHas88 = savedContact.length == 13
            if (numberHas88) {
                savedContact = savedContact.substring(2)

            }
            val userIsDatabase=users.find { it.phone==savedContact }
            if(userIsDatabase!=null){
                Log.d(TAG,"$userIsDatabase")
            }

        }



        withContext(Dispatchers.Main) {
            _users.value = updatedLocalContacts

        }
    }
}