package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserCollections
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.Contact
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.LocalContactsProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//after fetching the contact make eligible for garbage collect that is why
//content resolver declared as nullable
class UsersScreenViewModel(
    private var contentResolver: ContentResolver? = null
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    companion object {
        private const val TAG = "UsersScreenViewModelLog: "
    }
    fun log(message: String){
        Log.d(TAG,message)
    }

    init {
        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
            updateUser()
        }

    }

    fun onFriendRequestSent(user: User) {
        viewModelScope.launch {
            FriendManager().addNewFriendRequest(user.phone)
        }
    }


    private suspend fun updateUser() {
        contentResolver?.let { contentResolver ->
            var user = getUserWithLocalSavedIf(contentResolver)
            user = mergeWithAlreadyFriends(user)
            _users.value = mergeWithSentFriendRequest(user)
        }
        contentResolver = null//do not hold unnecessary reference to save memory performance

    }

    private suspend fun mergeWithAlreadyFriends(
        users: List<User>
    ): List<User> {
        val alreadyFriends = FriendManager().getFriends()
        return users.map { user ->
            val isFriend = alreadyFriends.find { friend -> friend.phone == user.phone } != null
            if (isFriend) user.copy(isFriend = true) else user
        }
    }

    private suspend fun mergeWithSentFriendRequest(
        users: List<User>
    ): List<User> {
        val friendRequests = FriendManager().getSentFriendRequest()
        log("Request: $friendRequests")
        return users.map { user ->
            val requestPending = friendRequests.find { request ->
                request.user.phone == user.phone
            } != null
            if (requestPending) user.copy(isSendRequest = true) else user
        }
    }


    private suspend fun getUserWithLocalSavedIf(contentResolver: ContentResolver): List<User> {
        val updatedLocalContacts = LocalContactsProvider(contentResolver).getContact()
        val users = UserCollections().allUsers()
        val savedMatchedPhoneNo = getUserPhoneThoseContactsSaved(updatedLocalContacts, users)

        return users.map { user ->
            if (savedMatchedPhoneNo.contains(user.phone)) user.copy(isAsLocalContact = true)
            else user
        }

    }

    private fun getUserPhoneThoseContactsSaved(
        savedContacts: List<Contact>,
        users: List<User>
    ): List<String> {
        val result = mutableListOf<String>()
        savedContacts.forEach { localContact ->
            var savedContact = localContact.phone.replace("[+\\s-]".toRegex(), "")
            val numberHas88 = savedContact.length == 13
            if (numberHas88) {
                savedContact = savedContact.substring(2)
            }
            val userIsDatabase = users.find { it.phone == savedContact }
            if (userIsDatabase != null) {
                result.add(userIsDatabase.phone)
            }

        }
        return result
    }
}