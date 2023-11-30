package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.auth.form

import androidx.compose.ui.graphics.Color

abstract class FormManager {
    abstract val field: List<FormTextFieldStateManager>
    open fun validate() {
        field.forEach {
            it.validate()

        }
    }

    open fun isValid()=field.all { it.state.value.errorMessage == null }

    open fun getData() = field.associate {
        val state = it.state.value
        state.label to state.text
    }
}