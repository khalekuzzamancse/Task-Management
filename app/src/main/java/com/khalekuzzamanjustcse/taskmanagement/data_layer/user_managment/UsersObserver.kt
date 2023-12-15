package com.khalekuzzamanjustcse.taskmanagement.data_layer.user_managment

import android.util.Log
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCollectionReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserEntity
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipInfo
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class User2(
    val name: String,
    val phone: String,
    val friendShipStatus: FriendShipStatus,
    val localContact: Boolean = false
)
object UsersObserver {

    private const val COLLECTION = "Users"
    private const val TAG = "UsersObserverLog: "
    private fun log(message: String) {
        Log.d(TAG, message)
    }

    private var friendShip: List<FriendShipInfo> = emptyList()
    private var userEntities = emptyList<UserEntity>()
    private val _users=MutableStateFlow(emptyList<User2>())
    val users= _users.asStateFlow()

    private fun updateUsers() {
       _users.value=userEntities.map {entity ->
            User2(
                name = entity.name,
                phone = entity.phone,
                friendShipStatus = friendShipState(entity.phone)
            )
        }
//        log("user:${userEntities}")
//        log("friendship:${friendShip}")
    }

    private fun onUsersUpdated(users: List<UserEntity>) {
        userEntities = users
        updateUsers()
    }

    init {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            FriendShipObserver.friendShipWithMe.collect {
                friendShip = it
               updateUsers()
            }
        }
        scope.launch {
            _users.collect{
                log("users:${it}")

            }
        }

    }

    private fun friendShipState(otherId: String): FriendShipStatus {
        val ship = friendShip.find { it.friendPhone == otherId }
        return if (ship != null) {
            if (ship.friendStatus >= 3)
                FriendShipStatus.Friend
            else
                FriendShipStatus.Pending
        } else FriendShipStatus.NoFriendShip
    }


    suspend fun subscribe(myUserId: String) {
        DatabaseCollectionReader(COLLECTION)
            .readObservable(classType = UserEntity::class.java)
            .collect { userEntities ->
                onUsersUpdated(
                    userEntities.filter { entity -> entity.phone != myUserId }
                )
            }
    }
}