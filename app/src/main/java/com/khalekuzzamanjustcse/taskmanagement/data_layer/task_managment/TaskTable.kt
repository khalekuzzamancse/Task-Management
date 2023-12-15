package com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.DocumentId
import kotlinx.coroutines.launch


data class TaskEntity @JvmOverloads constructor(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val assignerName: String = "",
    val assigneePhone: String = "",
    val dueDate: String = "",
    val notified: Boolean = false,
    val complete: Boolean = false
)

