package com.khalekuzzamanjustcse.taskmanagement.data_layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

val dummyTasks = listOf(
    TaskEntity(
        title = "Task 1",
        description = "Description for Task 1",
        assignerName = "Assigner 1",
        assigneePhone = "Assignee Phone 1",
        notified = false,
        complete = false
    ),
    TaskEntity(
        title = "Task 2",
        description = "Description for Task 2",
        assignerName = "Assigner 2",
        assigneePhone = "Assignee Phone 2",
        notified = false,
        complete = false
    ),
    TaskEntity(
        title = "Task 3",
        description = "Description for Task 3",
        assignerName = "Assigner 3",
        assigneePhone = "Assignee Phone 3",
        notified = false,
        complete = true
    )
    // Add more tasks as needed
)



data class TaskEntity @JvmOverloads constructor(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val assignerName: String = "",
    val assigneePhone: String = "",
    val dueDate: String = "",
    val notified: Boolean = false,
    val complete: Boolean = false
) {



    fun isEmpty(): Boolean {
        return title.isBlank() &&
                id.isBlank() &&
                description.isBlank() &&
                assignerName.isBlank() &&
                assigneePhone.isBlank() &&
                !notified &&
                !complete
    }
}

@Preview
@Composable
fun Preview() {
    LaunchedEffect(Unit) {
//        dummyTasks.forEach {
//            TaskTable().addTask(it)
//        }

    }


}


class TaskTable {
    private val db = FirebaseFirestore.getInstance()
    private val tasksCollection = db.collection("Tasks")
    fun addTask(task: TaskEntity) {
        var id=UUID.randomUUID().toString()
        if(task.id.isNotEmpty()){
            id=task.id
        }
        val taskDoc = tasksCollection.document(id)
        taskDoc.set(task)
            .addOnSuccessListener {
                // Task added successfully
            }
            .addOnFailureListener {
                // Handle the failure
            }
    }
    suspend fun addTask(task: TaskEntity2): Boolean = suspendCoroutine { continuation ->
        var id = UUID.randomUUID().toString()
        val taskHasId = task.taskId.isNotEmpty()
        if (taskHasId) {
            id = task.taskId
        }

        val taskDoc = tasksCollection.document(id)
        taskDoc.set(task)
            .addOnSuccessListener {
                continuation.resume(true)
            }
            .addOnFailureListener {
                continuation.resume(false)
            }
    }


    fun updateTask(task: TaskEntity) {
        val taskId = task.id
        if (taskId.isNotBlank()) {
            val taskDoc = tasksCollection.document(taskId)
            taskDoc.set(task)
                .addOnSuccessListener {
                    // Task updated successfully
                }
                .addOnFailureListener {
                    // Handle the failure
                }
        }
    }



    fun getTasks(): Flow<List<TaskEntity>> = callbackFlow {
        val callback = EventListener<QuerySnapshot> { value, _ ->
            value?.let { querySnapshot ->
                val taskList = mutableListOf<TaskEntity>()

                for (document in querySnapshot) {
                    val taskEntity = document.toObject(TaskEntity::class.java)
                    if (!taskEntity.isEmpty()) {
                        taskList.add(taskEntity)
                    }
                }
                trySend(taskList)
            }
        }
        val registration = tasksCollection.addSnapshotListener(callback)
        awaitClose { registration.remove() }
    }.onCompletion {
    }

}

