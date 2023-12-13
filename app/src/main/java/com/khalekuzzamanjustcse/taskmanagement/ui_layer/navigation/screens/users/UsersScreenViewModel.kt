package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.users

import android.content.ContentResolver
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.MyFriend
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserCollection
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserEntity
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.Contact
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.LocalContactsProvider
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
    val manager=FriendShipManager()

    companion object {
        private const val TAG = "UsersScreenViewModelLog: "
    }

    fun log(message: String) {
        Log.d(TAG, message)
    }

    init {
        viewModelScope.launch {
            updateUser()
            _isLoading.value = false
        }

    }

    fun onFriendRequestSent(user: User) {
        viewModelScope.launch {
            val myUserId=AuthManager().signedInUserPhone()
            Log.d( "OnFriendRequestSent", myUserId.toString())
            if(myUserId!=null){
                manager.sentFriendRequests(myUserId,user.phone)
            }


        }
    }

    private suspend fun updateUser() {
        val singedUserPhone= AuthManager().signedInUserPhone()
        if(singedUserPhone!=null) {
            contentResolver?.let { contentResolver ->
                _users.value = UserUtil(singedUserPhone)
                    .readLocalContacts(contentResolver)
                    .fetchRemoteEntity()
                    .matchLocalWithRemoteContacts()
                    .fetchMyFriends()
                    .mergeWithAlreadyFriends()
                    .fetchFriendRequest()
                    .mergeWithReceivedFriendRequest()
                    .users
            }
        }

        contentResolver = null
        //do not hold unnecessary reference to save memory performance

    }

}

@Preview
@Composable
fun Preview() {
    val resolver = LocalContext.current.contentResolver
    LaunchedEffect(Unit) {
        val util = UserUtil("2")
            .readLocalContacts(resolver)
            .fetchRemoteEntity()
            .matchLocalWithRemoteContacts()
            .fetchMyFriends()
            .mergeWithAlreadyFriends()
            .fetchFriendRequest()
            .mergeWithReceivedFriendRequest()
        Log.d("UserUtils: ", "$util")
    }


}

data class UserUtil(
    private val myUserId: String
) {
    private var savedContacts = emptyList<Contact>()
    private var remoteUsers = emptyList<UserEntity>()
    var users = emptyList<User>()
        private set
    private var friends = emptyList<MyFriend>()
    private var friendRequests = emptyList<MyFriend>()

    fun readLocalContacts(contentResolver: ContentResolver): UserUtil {
        savedContacts = LocalContactsProvider(contentResolver).getContact()
        return this
    }

    suspend fun fetchRemoteEntity(): UserUtil {
        remoteUsers = UserCollection().getUsers(myUserId)
        return this
    }

    fun matchLocalWithRemoteContacts(): UserUtil {
        val savedMatchedPhoneNumbers = getUserPhoneNumberThoseContactsSaved()
        users = remoteUsers.map { userEntity ->
            val user = toUser(userEntity)
            if (savedMatchedPhoneNumbers.contains(userEntity.phone))
                user.copy(isAsLocalContact = true)
            else user
        }
        return this
    }

    suspend fun fetchMyFriends(): UserUtil {
        friends = FriendShipManager().myFriendList(myUserId)
        return this

    }

    fun mergeWithAlreadyFriends(): UserUtil {
        users = users.map { user ->
            val isFriend = friends.find { friend -> friend.phone == user.phone } != null
            if (isFriend) user.copy(isFriend = true) else user
        }
        return this
    }

    suspend fun fetchFriendRequest(): UserUtil {
        friendRequests = FriendShipManager().getFriendRequest(myUserId)
        return this
    }

    fun mergeWithReceivedFriendRequest(): UserUtil {
        users = users.map { user ->
            val requestPending = friendRequests.find { request ->
                request.phone == user.phone
            } != null
            if (requestPending) user.copy(isSendRequest = true) else user
        }
        return this
    }

    private fun toUser(userEntity: UserEntity): User {
        return User(
            name = userEntity.name,
            phone = userEntity.phone,
            email = userEntity.email,
        )
    }

    private fun getUserPhoneNumberThoseContactsSaved(): List<String> {
        val result = mutableListOf<String>()
        savedContacts.forEach { localContact ->
            var savedContact = localContact.phone.replace("[+\\s-]".toRegex(), "")
            val numberHas88 = savedContact.length == 13
            if (numberHas88) {
                savedContact = savedContact.substring(2)
            }
            val userIsDatabase = remoteUsers.find { it.phone == savedContact }
            if (userIsDatabase != null) {
                result.add(userIsDatabase.phone)
            }
        }
        return result
    }

    override fun toString(): String {
        return "UserUtil(myFriends='$friends'\n, remoteUsers=$remoteUsers\n," +
                " remoteUserMatchWithLocalContacts=$users\n)" +
                "request=$friendRequests"
    }

}