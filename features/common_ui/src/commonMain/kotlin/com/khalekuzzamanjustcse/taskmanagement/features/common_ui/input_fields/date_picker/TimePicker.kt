package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.input_fields.date_picker

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


data class Time24hHour(
    val hour: Int,
    val minutes: Int,
) {
    val timeTo12Hour: String
        get() {
            val mm = getMinutes()
            val hh = getHours()
            val type = if (hour > 12) "PM" else "AM"
            return "$hh : $mm $type"
        }

    private fun getMinutes(): String {
        return if (minutes > 9) "$minutes" else "0$minutes"
    }

    private fun getHours(): String {
        if (hour > 12) {
            val hh = hour - 12
            return if (hh > 9) "$hh" else "0$hh"
        }
        return if (hour > 9) "$hour" else "0$hour"

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(
    modifier: Modifier = Modifier,
    state: Time24hHour,
    onUpdateState: (Time24hHour) -> Unit,
) {
    val timeState = rememberTimePickerState()
    var openDialog by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = state.timeTo12Hour,
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.clickable {
                    openDialog = true
                }
            )
        }
    )
    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        value = state.timeTo12Hour
                        onUpdateState(
                            Time24hHour(
                                hour = timeState.hour,
                                minutes = timeState.minute
                            )
                        )
                    },
                    enabled = true
                ) {
                    Text("OK")

                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false

                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            TimePicker(
                state = timeState
            )
        }
    }


}