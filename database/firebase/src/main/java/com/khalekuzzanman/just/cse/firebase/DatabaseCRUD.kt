package com.khalekuzzanman.just.cse.firebase

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot


@Preview
@Composable
fun D() {
//    DatabaseCRUD().read()
}
class DatabaseCRUD {
     fun read(){
        val callback = OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    Log.d("DatabaseModule","$document")
                }
            }
        }

        val db = FirebaseFirestore.getInstance()
        val cities: CollectionReference = db.collection("Users")
        val listOfDocumentSnapshot: Query = cities
        val snapshotTask: Task<QuerySnapshot> = listOfDocumentSnapshot.get()
        snapshotTask.addOnCompleteListener(callback)

    }
}