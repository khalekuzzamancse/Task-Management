package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.UserInfoCard


@Composable
fun ContactScreen(
    viewModel: DeviceContactViewModel,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = viewModel.users.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        showSnackBar = viewModel.showSnackBar.collectAsState().value,
        snackBarMessage = viewModel.snackBarMessage,
        itemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone, savedInContact = false)
        },
        screenTitle = "Contacts",
        onBack = onNavIconClicked,
    )

}


data class Contact(
    val name: String,
    val phone: String,
)

class FetchContact(private val context: Context) {
    fun getContact(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val contentResolver = context.contentResolver
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = contentResolver.query(uri, projection, null, null, null)
        while (cursor!!.moveToNext()) {
            val nameColumnId =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val name = cursor.getString(nameColumnId)
            val numberColumnId =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberColumnId)
            contacts.add(Contact(name, number))
            Log.i("ContactFetched", "$name : $number")
        }
        cursor.close()
        return contacts.sortedBy { it.name }
    }
}

class LocalContactsProvider(private val contentResolver: ContentResolver) {
    fun getContact(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = contentResolver.query(uri, projection, null, null, null)
        while (cursor!!.moveToNext()) {
            val nameColumnId =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val name = cursor.getString(nameColumnId)
            val numberColumnId =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberColumnId)
            contacts.add(Contact(name, number))
            Log.i("ContactFetched", "$name : $number")
        }
        cursor.close()
        return contacts.sortedBy { it.name }
    }
}
