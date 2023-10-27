package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.auth.form

class FieldValidator {
    fun validatePassword(password: String): String? {
        return if (password.trim().length < 6) "Password Length at least 6" else null
    }

    fun validatePhoneNo(phoneNumber: String): String? {
        val phoneRegex = "^01[0-9]{9}$"
        return if (phoneNumber.trim()
                .matches(phoneRegex.toRegex())
        ) null else "Invalid phone number"
    }


    fun validateEmail(email: String): String? {
        val emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[A-Za-z]{2,}$"

        if (!email.trim().matches(emailRegex.toRegex())) {
            return "Invalid Email"
        }

        val parts = email.trim().split("@")
        if (parts.size != 2) {
            return "Invalid Email"
        }

        val domainParts = parts[1].split(".")
        if (domainParts.size < 2) {
            return "Invalid Email"
        }

        return null
    }

    fun validateEmpty(text: String): String? {
        return if (text.trim().isEmpty()) "Cannot be empty" else null
    }
}