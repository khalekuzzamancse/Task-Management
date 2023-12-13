package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun UserCollectionPreview() {
    val dummyUser=UserEntity(
        name = "Nada",
        phone = "2",
        email = "baka@gmail.com",
    )
    FlowRow {
        MyButton(onClick = {
            val user = UserCollection().createUser(dummyUser)
            Log.d("UserCollection:create", "$user")
        }, label = "Create")
        MyButton(onClick = {
            val user = UserCollection().getUser("2")
            Log.d("UserCollection:read", "$user")
        }, label = "Read")
    }
}

data class UserEntity @JvmOverloads constructor(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
)

class UserCollection {
    private val databaseCRUD = DatabaseCRUD()

    companion object {
        private const val COLLECTION_NAME = "Users"
    }

    suspend fun getUser(userPhone: String): UserEntity? {
        return databaseCRUD.read<UserEntity>(
            COLLECTION_NAME,
            docId = userPhone
        )
    }

    suspend fun createUser(userEntity: UserEntity): Boolean {
        return databaseCRUD.create(COLLECTION_NAME, userEntity, docID = userEntity.phone)
    }

}