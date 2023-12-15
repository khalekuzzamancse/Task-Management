package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.MyFriend
import com.khalekuzzamanjustcse.taskmanagement.data_layer.notification.ObservableFriendShip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FriendListScreenViewModel : ViewModel() {
    private val friends = MutableStateFlow<List<MyFriend>>(emptyList())
    val users = friends.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            ObservableFriendShip.myFriends.collect { friendships ->
                friends.value = friendships.map { friendship ->
                    MyFriend(
                        name = friendship.friendName,
                        phone = friendship.friendPhone,
                        friendShipId = friendship.friendShipId
                    )
                }
            }
        }

    }
}
