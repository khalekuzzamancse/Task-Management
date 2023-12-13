package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TaskDemo() {
    val taskToAdd = TaskToAdd(
        title = "02 Dummy Task",
        description = "02 This is a dummy task for testing",
        assignerIdentifier = "017388",
        dueDate = 12344,
        assignee = listOf("0123", "0134")
    )
    val scope = rememberCoroutineScope()

    FlowRow {
        Button(onClick = {
            scope.launch {
                TaskTable2().addTask(taskToAdd)

            }

        }) {
            Text(text = "Add Task")
        }
        Button(onClick = {
            scope.launch {
                val tasks = TaskTable2().getAssignedTasks("0134")
                Log.d("TaskDemoCom:", "$tasks")

            }

        }) {
            Text(text = "Fetch Tasks")
        }
        Button(onClick = {
            scope.launch {
                TaskTable2().updateTaskStage(
                    assignedTask = AssignedTask(
                        assignmentId = "YF4XE6LKI5GJy0ufNtzx",
                        assigneeId = "0123",
                        taskStateId = "2",
                        taskId = "Dummy Task-017388"
                    ),
                    newState = "4"
                )
            }

        }) {
            Text(text = "Update Tasks Stage")
        }


    }

}

data class TaskToAdd(
    val title: String,
    val description: String,
    val dueDate: Long,
    val assignerIdentifier: String,
    val assignee: List<String>
) {
    private val taskPrimaryKey = "$title-$assignerIdentifier"
    fun getEntity(): TaskEntity2 {
        return TaskEntity2(
            taskId = taskPrimaryKey,
            title = title,
            description = description,
            dueTime = dueDate,
            assignerId = assignerIdentifier
        )
    }

    fun getTaskAssignee() = assignee.map { assignerPrimaryKey ->
        AssignedTask(
            taskId = taskPrimaryKey,
            assigneeId = assignerPrimaryKey,
            taskStateId = "1"
        )
    }
}

data class TaskEntity2 @JvmOverloads constructor(
    @DocumentId val taskId: String = "",
    val title: String = "",
    val description: String = "",
    val dueTime: Long = 0,
    val assignerId: String = "",
)

data class AssignedTask @JvmOverloads constructor(
    @DocumentId val assignmentId: String = "",
    val taskId: String = "",
    val taskStateId: String = "",
    val assigneeId: String = "",
    val assignedTime: Long = 0,
    val completionTime: Long = 0,
)


data class MyAssignedTask(
    val title: String,
    val description: String,
    val assignerName: String,
    val assignerPhone: String,
    val dueDate: Long,
    val taskAssignedId: String,
) {
    companion object {
        fun toAssignedTask(assignedTask: AssignedTask, task: TaskEntity2): MyAssignedTask {
            return MyAssignedTask(
                title = task.title,
                description = task.description,
                assignerName = "--",
                assignerPhone = task.assignerId,
                dueDate = task.dueTime,
                taskAssignedId = assignedTask.assignmentId
            )
        }
    }
}


class TaskTable2 {
    companion object {
        private const val TASKS_COLLECTION = "Tasks"
        private const val TASK_ASSIGNEE_COLLECTION = "AssignedTask"
        private const val ASSIGN_ID_FIELD = "assigneeId"

    }

    private val db = FirebaseFirestore.getInstance()
    private val taskCollection = db.collection(TASKS_COLLECTION)
    private val assignedTaskCollection = db.collection(TASK_ASSIGNEE_COLLECTION)


    suspend fun addTask(task: TaskToAdd) {
        val isSuccess = addToTaskCollection(task.getEntity())
        if (isSuccess) {
            task.getTaskAssignee().forEach { taskAssignee ->
                addAssignee(taskAssignee)
            }
        }
    }

    private suspend fun addToTaskCollection(task: TaskEntity2): Boolean =
        suspendCoroutine { continuation ->
            var id = UUID.randomUUID().toString()
            val taskHasNotId = task.taskId.isNotEmpty()
            if (taskHasNotId) {
                id = task.taskId
            }
            val documentReference = taskCollection.document(id)
            documentReference.set(task)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }

    private suspend fun addAssignee(assignee: AssignedTask): Boolean =
        suspendCoroutine { continuation ->
            val documentReference = assignedTaskCollection.document()
            documentReference.set(assignee)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }


    suspend fun updateTask(task: TaskEntity2): Boolean = suspendCoroutine { continuation ->
        val taskId = task.taskId
        val taskHasId = taskId.isNotBlank()
        if (taskHasId) {
            val taskDoc = taskCollection.document(taskId)
            taskDoc
                .set(task)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }
        } else {
            continuation.resume(false)
        }
    }

    suspend fun updateTaskStage(assignedTask: AssignedTask, newState: String): Boolean =
        suspendCoroutine { continuation ->
            if (assignedTask.assignmentId.isNotBlank()) {
                val taskDoc = assignedTaskCollection.document(assignedTask.assignmentId)
                taskDoc
                    .set(assignedTask.copy(taskStateId = newState))
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            } else {
                continuation.resume(false)
            }
        }

    suspend fun getAssignedTasks(signedUserPhone: String): List<MyAssignedTask> {
        val tasks = mutableListOf<MyAssignedTask>()
        val refs = getAssignedTaskReferences(signedUserPhone)
      //  Log.d("TaskDemoCom:Ref", "$refs")
        refs.forEach { taskAssignee ->
            val task = getTasks(taskAssignee.taskId)
            if (task != null) tasks.add(MyAssignedTask.toAssignedTask(taskAssignee, task))
        }
        return tasks
    }

    private suspend fun getTasks(taskPK: String): TaskEntity2? {
        return try {
            val documentSnapshot = taskCollection.document(taskPK)
                .get()
                .await()
            if (documentSnapshot.exists())
                documentSnapshot.toObject(TaskEntity2::class.java)
            else null
        } catch (e: Exception) {
            null
        }
    }


    private suspend fun getAssignedTaskReferences(signedUserPhone: String): List<AssignedTask> {
        return suspendCancellableCoroutine { continuation ->
            val callback = EventListener<QuerySnapshot> { value, error ->
                if (error != null) {
                    // Handle the error
                    continuation.resumeWith(Result.failure(error))
                    return@EventListener
                }

                value?.let { querySnapshot ->
                    val assignedTasksRef = mutableListOf<AssignedTask>()
                    for (document in querySnapshot) {
                        val taskEntity = document.toObject(AssignedTask::class.java)
                        assignedTasksRef.add(taskEntity)
                    }
                    continuation.resume(assignedTasksRef)
                }
            }

            val query = assignedTaskCollection
                .whereEqualTo(ASSIGN_ID_FIELD, signedUserPhone)

            val registration = query.addSnapshotListener(callback)

            // When the coroutine is cancelled, remove the snapshot listener
            continuation.invokeOnCancellation {
                registration.remove()
            }
        }
    }


}


















/*


   fun getTasks(): Flow<List<TaskEntity2>> = callbackFlow {
        val callback = EventListener<QuerySnapshot> { value, _ ->
            value?.let { querySnapshot ->
                val taskList = mutableListOf<TaskEntity2>()
                for (document in querySnapshot) {
                    val taskEntity = document.toObject(TaskEntity2::class.java)
                    var isNotEmpty: Boolean
                    taskEntity.apply {
                        isNotEmpty = !(title.isBlank() &&
                                taskId.isBlank() &&
                                description.isBlank() &&
                                assignerId.isBlank())
                    }
                    if (isNotEmpty) {
                        taskList.add(taskEntity)
                    }
                }
                trySend(taskList)
            }
        }
        val registration = taskCollection.addSnapshotListener(callback)
        awaitClose { registration.remove() }
    }.onCompletion {}
 */