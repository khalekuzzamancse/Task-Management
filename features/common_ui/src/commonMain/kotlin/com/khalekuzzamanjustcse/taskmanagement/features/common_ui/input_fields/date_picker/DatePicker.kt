package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.input_fields.date_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDate(milliseconds: Long): String {
    val date = Date(milliseconds)
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(date)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    modifier: Modifier=Modifier,
    onDateSelected: (String) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {},
        readOnly =true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.clickable {
                   openDialog=true
                }
            )
        }
    )
    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled =
            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        val date =
                            datePickerState.selectedDateMillis?.let { getDate(it) }.toString()
                        onDateSelected(date)
                        value = date

                    },
                    enabled = confirmEnabled.value
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
            DatePicker(
                showModeToggle = false,
                state = datePickerState
            )
        }
    }
}
