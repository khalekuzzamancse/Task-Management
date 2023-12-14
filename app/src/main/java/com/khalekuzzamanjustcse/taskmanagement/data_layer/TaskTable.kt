package com.khalekuzzamanjustcse.taskmanagement.data_layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Filter
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskDoer
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskOwnedByMe


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

@Preview
@Composable
fun Preview() {
    LaunchedEffect(Unit) {
//        dummyTasks.forEach {
//            TaskTable().addTask(it)
//        }

    }


}

class TaskTable(private val signedInUserId: String,
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


    suspend fun getAssignedNotNotifiedTask(): List<MyAssignedTask> {
        val ref = getAssignTaskRef().filter { it.taskStateId == STATE_CREATED_NOT_NOTIFIED }
        return getAssignTasks(ref)
    }

    suspend fun getCompletedNotNotifiedTask(): List<MyAssignedTask> {
        val ref = getAssignTaskRef().filter { it.taskStateId == STATE_COMPLETED_NOT_NOTIFIED }
        return getAssignTasks(ref)
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
            result+= TaskOwnedByMe(
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
        where = Filter.equalTo(FIELD_ASSIGNER_ID, signedInUserId)
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
            where = Filter.equalTo(ASSIGN_ID_FIELD, signedInUserId)
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


