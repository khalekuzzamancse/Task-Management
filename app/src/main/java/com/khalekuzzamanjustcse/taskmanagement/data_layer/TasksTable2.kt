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
import com.google.firebase.firestore.Filter
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskDoer
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskOwnedByMe
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TaskDemo() {
    val taskToAdd = TaskToAdd(
        title = "02 Dummy Task",
        description = "02 This is a dummy task for testing",
        assignerIdentifier = "01571378537",
        dueDate = "12344",
        assignee = listOf("01571207787", "01625337883")
    )
    val scope = rememberCoroutineScope()

    FlowRow {
        Button(onClick = {
            scope.launch {
                TaskTable2("01571378537").createTask(taskToAdd)

            }

        }) {
            Text(text = "Add Task")
        }
        Button(onClick = {
            scope.launch {
                val tasks = TaskTable2("01571378537").getAssignedTasks("0134")
                Log.d("TaskDemoCom:", "$tasks")

            }

        }) {
            Text(text = "Fetch Tasks")
        }
        Button(onClick = {
            scope.launch {
                TaskTable2("01571378537").makeAssignedTaskNotified("RQO1v9HC9pk7JPxM5Khm")
            }

        }) {
            Text(text = "Update Tasks Stage")
        }
        Button(onClick = {
            scope.launch {
                val taskTable = TaskTable2("01571378537")
                val tasks = taskTable.getAssignedNotNotifiedTask()
                tasks.forEach {
                    taskTable.makeAssignedTaskNotified(it.taskAssignedId)
                }
                Log.d("NewlyAssingnedTask", "$tasks")

            }

        }) {
            Text(text = "assinged  task")
        }
        Button(onClick = {
            scope.launch {
                val taskTable = TaskTable2("01571378537")
                val tasks = taskTable.getCompletedNotNotifiedTask()
                tasks.forEach {
                    taskTable.makeTaskCompletedTaskNotified(it.taskAssignedId)
                }

            }

        }) {
            Text(text = "Complete  not notified  task")
        }
        Button(onClick = {
            scope.launch {
                val taskTable = TaskTable2("01571378537")
                val tasks = taskTable.myAssignedCompletedUnNotifiedTask()
                tasks.forEach {
                    Log.d(
                        "MyAssignedTask:ComputedBY",
                        "${it.task.title} completed by ${it.completer.name}"
                    )
                    taskTable.makeTaskCompletedTaskNotified(it.taskAssignedId)
                }

            }

        }) {
            Text(text = "MY ASSIGNED COMPLETED TASK")
        }


    }

}

data class TaskToAdd(
    val title: String,
    val description: String,
    val dueDate: String,
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
        AssignedTaskRelation(
            taskId = taskPrimaryKey,
            assigneeId = assignerPrimaryKey,
            taskStateId = 1
        )
    }
}

data class TaskEntity2 @JvmOverloads constructor(
    @DocumentId val taskId: String = "",
    val title: String = "",
    val description: String = "",
    val dueTime: String = "0",
    val assignerId: String = "",
)

data class AssignedTaskRelation @JvmOverloads constructor(
    @DocumentId val assignmentId: String = "",
    val taskId: String = "",
    val taskStateId: Int = 1,
    val assigneeId: String = "",
    val assignedTime: Long = 0,
    val completionTime: Long = 0,
)


data class MyAssignedTask(
    val title: String,
    val description: String,
    val assignerName: String,
    val assignerPhone: String,
    val dueDate: String,
    val taskAssignedId: String,
) {
    companion object {
        fun toAssignedTask(
            assignedTaskRelation: AssignedTaskRelation,
            task: TaskEntity2
        ): MyAssignedTask {
            return MyAssignedTask(
                title = task.title,
                description = task.description,
                assignerName = "",
                assignerPhone = task.assignerId,
                dueDate = task.dueTime,
                taskAssignedId = assignedTaskRelation.assignmentId
            )
        }
    }
}

data class CompletedTaskUnNotified(
    val task: TaskEntity2,
    val completer: UserEntity,
    val taskAssignedId: String
)


class TaskTable2(
    val myUserId: String,
) {
    companion object {
        private const val TASKS_COLLECTION = "Tasks"
        private const val COLLECTION_ASSIGNED_TASK = "AssignedTask"
        private const val ASSIGN_ID_FIELD = "assigneeId"
        private const val FIELD_STATE_ID = "taskStateId"
        private const val FIELD_ASSIGNER_ID = "assignerId"
        private const val FIELD_TASK_ID = "taskId"
        private const val STATE_CREATED_NOT_NOTIFIED = 1
        private const val STATE_CREATED_NOTIFIED = 2
        private const val STATE_COMPLETED_NOT_NOTIFIED = 3
        private const val STATE_COMPLETED_NOTIFIED = 4

    }


    private val databaseCRUD = DatabaseCRUD()


    suspend fun createTask(task: TaskToAdd): Boolean {
        val entity = task.getEntity()
        val isSuccess = databaseCRUD.create(
            collectionName = TASKS_COLLECTION,
            entity = entity,
            docID = entity.taskId
        )
        if (isSuccess) {
            task.getTaskAssignee().forEach { taskAssignee ->
                addAssignee(taskAssignee)
            }
        }
        return isSuccess
    }

    suspend fun getAssignedNotNotifiedTask(): List<MyAssignedTask> {
        val ref = getAssignTaskRef().filter { it.taskStateId == STATE_CREATED_NOT_NOTIFIED }
        return getAssignTasks(ref)
    }

    suspend fun getCompletedNotNotifiedTask(): List<MyAssignedTask> {
        val ref = getAssignTaskRef().filter { it.taskStateId == STATE_COMPLETED_NOT_NOTIFIED }
        return getAssignTasks(ref)
    }

    suspend fun makeAssignedTaskNotified(assignedTaskId: String): Boolean {
        return databaseCRUD.update(
            COLLECTION_ASSIGNED_TASK,
            Updater(
                docID = assignedTaskId,
                field = FIELD_STATE_ID,
                value = STATE_CREATED_NOTIFIED
            )
        )
    }

    suspend fun makeTaskCompleted(assignedTaskId: String): Boolean {
        return databaseCRUD.update(
            COLLECTION_ASSIGNED_TASK,
            Updater(
                docID = assignedTaskId,
                field = FIELD_STATE_ID,
                value = STATE_COMPLETED_NOT_NOTIFIED
            )
        )
    }

    suspend fun makeTaskCompletedTaskNotified(assignedTaskId: String): Boolean {
        return databaseCRUD.update(
            COLLECTION_ASSIGNED_TASK,
            Updater(
                docID = assignedTaskId,
                field = FIELD_STATE_ID,
                value = STATE_COMPLETED_NOTIFIED
            )
        )
    }


    private suspend fun addAssignee(assignee: AssignedTaskRelation): Boolean {
        return databaseCRUD.create(
            COLLECTION_ASSIGNED_TASK, assignee
        )

    }

    /*
    Find the taskId that I assigned.
    then got to the AssignedTask collection,
    find the task ref that is my assigned list
    then filter the ref that state==3
    then fetch those tasks again by id
     */

    suspend fun myAssignedCompletedUnNotifiedTask(): List<CompletedTaskUnNotified> {
        val tasks = readMyAssignedTaskEntity()
        val assignedTask = readMyAssigneeRef(tasks.map { it.taskId })
        val userCollection = UserCollection()
        val responses = mutableListOf<CompletedTaskUnNotified>()
        assignedTask.forEach { assignedTaskRelation ->
            val user = userCollection.getUser(assignedTaskRelation.assigneeId)
            val task = tasks.find { it.taskId == assignedTaskRelation.taskId }
            if (task != null && user != null) {
                responses += CompletedTaskUnNotified(task, user, assignedTaskRelation.assignmentId)
            }
        }
        return responses
    }


    suspend fun taskOwnedByMe():List<TaskOwnedByMe> {
        val userCollection = UserCollection()
        val tasksByMe = readMyAssignedTaskEntity()
        val result = mutableListOf<TaskOwnedByMe>()
        tasksByMe.forEach { task ->
            val relations = readAssignedTaskRelation(task.taskId)
            val doers = mutableListOf<TaskDoer>()
            relations.forEach { relation ->
                val doer = userCollection.getUser(relation.assigneeId)
                if (doer != null) {
                    doers += TaskDoer(
                        name = doer.name,
                        phone = doer.phone,
                        status = decodeTaskState(relation.taskStateId)
                    )
                }
            }
            result+=TaskOwnedByMe(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                dueDate = task.dueTime,
                doers=doers
            )
        }
        return result
    }

    private fun decodeTaskState(state: Int): String {
        return when (state) {
            1 -> "Unseen"
            2 -> "Seen"
            else -> "Completed"
        }
    }

    private suspend fun readMyAssignedTaskEntity() = databaseCRUD.read<TaskEntity2>(
        collection = TASKS_COLLECTION,
        where = Filter.equalTo(FIELD_ASSIGNER_ID, myUserId)
    )

    private suspend fun readAssignedTaskRelation(taskId: String): List<AssignedTaskRelation> {
        val relations = mutableListOf<AssignedTaskRelation>()
        relations += databaseCRUD.read(
            collection = COLLECTION_ASSIGNED_TASK,
            where = Filter.equalTo(FIELD_TASK_ID, taskId)
        )
        return relations
    }

    private suspend fun readMyAssigneeRef(
        myAssignedTaskIds: List<String>
    ): List<AssignedTaskRelation> {
        val refs = mutableListOf<AssignedTaskRelation>()
        myAssignedTaskIds.forEach { taskId ->
            refs += databaseCRUD.read(
                collection = COLLECTION_ASSIGNED_TASK,
                where = Filter.equalTo(FIELD_TASK_ID, taskId)
            )
        }
        return refs.filter { it.taskStateId == STATE_COMPLETED_NOT_NOTIFIED }
    }


    private suspend fun getAssignTaskRef(): List<AssignedTaskRelation> {
        return databaseCRUD.read(
            collection = COLLECTION_ASSIGNED_TASK,
            where = Filter.equalTo(ASSIGN_ID_FIELD, myUserId)
        )
    }

    private suspend fun getAssignTasks(refs: List<AssignedTaskRelation>): List<MyAssignedTask> {
        val tasks = mutableListOf<MyAssignedTask>()
        refs.forEach { taskAssignee ->
            val task = databaseCRUD.read<TaskEntity2>(
                collection = TASKS_COLLECTION,
                docId = taskAssignee.taskId
            )
            if (task != null) tasks.add(MyAssignedTask.toAssignedTask(taskAssignee, task))
        }
        return tasks
    }

    suspend fun getAssignedTasks(signedUserPhone: String): List<MyAssignedTask> {
        val tasks = mutableListOf<MyAssignedTask>()
        val refs = databaseCRUD.read<AssignedTaskRelation>(
            collection = COLLECTION_ASSIGNED_TASK,
            where = Filter.equalTo(ASSIGN_ID_FIELD, signedUserPhone)
        )
        refs.forEach { taskAssignee ->
            val task = databaseCRUD.read<TaskEntity2>(
                collection = TASKS_COLLECTION,
                docId = taskAssignee.taskId
            )
            if (task != null){
                val assigner=UserCollection().getUser(task.assignerId)
                if (assigner != null){
                    tasks.add(
                        MyAssignedTask
                            .toAssignedTask(taskAssignee, task)
                            .copy(assignerName = assigner.name+"(${assigner.phone})")
                    )
                }

            }
        }
        return tasks
    }

}

