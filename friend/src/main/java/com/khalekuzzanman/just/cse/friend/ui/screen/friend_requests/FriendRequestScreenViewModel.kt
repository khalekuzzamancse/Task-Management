//package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.friend_requests
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipManager
//import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipObserver
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class FriendRequestScreenViewModel : ViewModel() {
//    fun acceptFriendRequest(friendshipId: String) {
//        viewModelScope.launch {
//            FriendShipManager().acceptRequest(friendshipId)
//        }
//    }
//
//    val requests = FriendShipObserver.requests
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading = _isLoading.asStateFlow()
//
//}