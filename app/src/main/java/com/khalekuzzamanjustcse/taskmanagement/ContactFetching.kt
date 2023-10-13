package com.khalekuzzamanjustcse.taskmanagement

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ContactGet() {
    val context= LocalContext.current
    val contacts=FetchContact(context).getContact()
    LazyColumn {
        items(items=contacts) { contact ->
            ContactCard(contact)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}
@Composable
fun ContactCard(contact: Contact) {
    Card(modifier= Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation =CardDefaults.cardElevation()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Contact Icon",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = contact.name, fontWeight = FontWeight.Bold)
                Text(text = contact.phone, color = Color.Gray)
            }
        }
    }

}



data class Contact(
    val name:String,
    val phone:String,
)

class FetchContact (private val context: Context) {
       fun getContact():List<Contact>{
           val contacts= mutableListOf<Contact>()
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
                contacts.add(Contact(name,number))
                Log.i("ContactFetched", "$name : $number")
            }
            cursor.close()
           return contacts
        }
}
