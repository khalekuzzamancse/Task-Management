package com.khalekuzzamanjustcse.taskmanagement.data_layer

import java.util.Scanner


class Button(val click: () -> Unit)

fun main() {
    var userName = ""
    var password = ""
    val loginButton = Button {
        println("")
        println("Login Button is Clicked.")
        println("UserName: $userName \nUserEmail: $password")
        println("Login successfully")
    }

    val showLoginScreen = true
    val loginThread: Thread
    while (showLoginScreen) {
        val scanner = Scanner(System.`in`)
        print("Enter your username:")
        userName = scanner.nextLine()
        print("Enter your password:")
        password = scanner.nextLine()
        print("For Login type login else press type anything: ")
        val clickEvent = scanner.nextLine()
        val isLoginButtonClicked = clickEvent == "login"
        if (isLoginButtonClicked) {
            loginThread = Thread {
                loginButton.click()
            }
            loginThread.start()
            loginThread.join() // Wait for the button click thread to finish
            break
        }
    }
    println("Login Screen terminated.")
}
