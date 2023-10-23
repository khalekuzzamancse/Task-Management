package com.khalekuzzamanjustcse.taskmanagement.ui

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@Composable
fun ContactScreen(
    contacts: List<Contact>,
    onNavIconClicked: () -> Unit,
) {
    GenericListScreen(
        items = contacts,
        itemContent = { contact ->
            UserInfoCard(name = contact.name, phone = contact.phone)
        },
        screenTitle = "Contacts",
        onBack =onNavIconClicked
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
