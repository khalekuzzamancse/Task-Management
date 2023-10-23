package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel(){
    //Handling registration
    private val _openRegistrationFrom=MutableStateFlow(false)
    val openRegistrationFrom=_openRegistrationFrom.asStateFlow()
    fun onRegistrationRequest(){
        _openRegistrationFrom.value=true
    }
    fun onRegistrationDone(){
        _openRegistrationFrom.value=false
    }


}