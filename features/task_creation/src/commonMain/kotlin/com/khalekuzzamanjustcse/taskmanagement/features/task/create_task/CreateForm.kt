package com.khalekuzzamanjustcse.taskmanagement.features.task.create_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.just.cse.digital_diary.features.common_ui.form.FormLayout
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form.LabelLessTextFieldState
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.input_fields.date_picker.Time24hHour
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.input_fields.date_picker.MyDatePicker
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.input_fields.date_picker.MyTimePicker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow



data class CreatedTask(
    val title: String,
    val description: String,
    val dueDate: String,
    val dueTime: Time24hHour
)

class CreateTaskFormState {
    private val _titleState = MutableStateFlow(LabelLessTextFieldState())
    private val _descriptionState = MutableStateFlow(LabelLessTextFieldState())
    private val _dueDateState = MutableStateFlow(LabelLessTextFieldState())

    private val _dueTimeState = MutableStateFlow(Time24hHour(12, 12))

    //
    val titleState = _titleState.asStateFlow()
    val descriptionState = _descriptionState.asStateFlow()
    val dueTimeState = _dueTimeState.asStateFlow()

    //
    fun onTitleChanged(title: String) {
        _titleState.value = _titleState.value.copy(value = title)
    }

    fun onDescriptionChanged(description: String) {
        _descriptionState.value = _descriptionState.value.copy(value = description)
    }

    fun onDueDateChanged(dueDate: String) {
        _dueDateState.value = _descriptionState.value.copy(value = dueDate)
    }
    fun onDueTimeChanged(dueTime: Time24hHour) {
        _dueTimeState.value = dueTime
        println(dueTime)
    }

    fun getCreatedTaskInfo(): CreatedTask {
        val title = _titleState.value.value
        val description = _descriptionState.value.value
        val dueDate = _dueDateState.value.value
        val hour = _dueTimeState.value.hour
        val minutes = _dueTimeState.value.minutes
        return CreatedTask(
            title=title,
            description=description,
            dueDate=dueDate,
            dueTime = Time24hHour(hour = hour, minutes = minutes)
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskForm(
    isHorizontal: Boolean,
    title: LabelLessTextFieldState,
    onTitleChanged: (String) -> Unit,
    descriptionState: LabelLessTextFieldState,
    onDescriptionState: (String) -> Unit,
    timePickerState: Time24hHour,
    onDueTimeUpdate:(Time24hHour)->Unit,
    onDueDateChanged: (String) -> Unit,
    //optional
    fieldModifier: Modifier = Modifier.fillMaxWidth(),
    formModifier: Modifier = Modifier.fillMaxWidth(),
    verticalGap: Dp = 10.dp,

    ) {

    if (isHorizontal) {
        FormLayout(
            eachRow1stChildMaxWidth = 200.dp,
            modifier = formModifier,
            verticalGap = verticalGap,
            horizontalGap = 8.dp
        ) {
            Text(text = "Title")
            TaskInputField(
                modifier = fieldModifier,
                state = title,
                onValueChanged = onTitleChanged
            )
            Text(text = "Description")
            TaskInputField(
                modifier = fieldModifier,
                leadingIcon = Icons.Default.Lock,
                state = descriptionState,
                onValueChanged = onDescriptionState,
            )
        }

    } else {
        Column(fieldModifier) {
            Text(text = "Title")
            TaskInputField(
                modifier = fieldModifier,
                leadingIcon = null,
                state = title,
                onValueChanged = onTitleChanged
            )
            Text(text = "Description")
            TaskInputField(
                modifier = fieldModifier,
                state = descriptionState,
                onValueChanged = onDescriptionState,
            )
            Text(text = "Due Date")
            MyDatePicker(
                modifier = fieldModifier,
                onDateSelected = onDueDateChanged
            )
            Text(text = "Due Time")
            MyTimePicker(
                modifier = fieldModifier,
                state = timePickerState,
                onUpdateState = onDueTimeUpdate
            )
        }

    }

}


