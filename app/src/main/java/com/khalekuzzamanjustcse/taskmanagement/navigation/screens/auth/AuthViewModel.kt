package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    //Handling registration
    private val _openRegistrationFrom = MutableStateFlow(false)
    val openRegistrationFrom = _openRegistrationFrom.asStateFlow()
    fun onRegistrationRequest() {
        _openRegistrationFrom.value = true
    }

    fun onRegistrationDone() {
        _openRegistrationFrom.value = false
    }

    //Login
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()



    //Snack bar management
    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage = _snackBarMessage.asStateFlow()
    private val _showSnackBar = MutableStateFlow(false)
    val showSnackBar = _showSnackBar.asStateFlow()
    //

    fun onLoginRequest() {
        AuthManager(
            onSuccess = {

                _snackBarMessage.value = "Login successfully"
                _showSnackBar.value = true
                viewModelScope.launch {
                    delay(300)
                    withContext(Dispatchers.Main){
                        _showSnackBar.value = false
                    }

                }



            },
            onFailed = {
                _snackBarMessage.value =  "Login failed"
                _showSnackBar.value = true
                viewModelScope.launch {
                    delay(300)
                    withContext(Dispatchers.Main){
                        _showSnackBar.value = false
                    }

                }

            }
        ).signIn(
            email = loginState.value.email,
            password = loginState.value.password
        )
    }


}