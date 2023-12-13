package com.khalekuzzamanjustcse.taskmanagement.data_layer

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class Updater(
    val docID: String,
    val field: String,
    val value: Any
)

class DatabaseCRUD {
    suspend fun create(collectionName: String, entity: Any, docID: String? = null): Boolean =
        suspendCoroutine { continuation ->
            val collectionRef = Firebase.firestore.collection(collectionName)

            val documentReference = if (docID == null)
                collectionRef.document()
            else
                collectionRef.document(docID)

            documentReference.set(entity)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }

    suspend inline fun <reified T> read(collectionName: String, docId: String): T? {
        return try {
            val collectionRef = Firebase.firestore.collection(collectionName)
            val documentSnapshot = collectionRef.document(docId)
                .get()
                .await()
            if (documentSnapshot.exists())
                documentSnapshot.toObject(T::class.java)
            else null
        } catch (e: Exception) {
            null
        }
    }

    suspend inline fun <reified T> read(
        collectionName: String,
        predicate: Filter
    ): List<T> {
        val collectionRef = Firebase.firestore.collection(collectionName)
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                collectionRef
                    .where(predicate)
                    .get()
                    .await()
            }
            asList(querySnapshot)
        } catch (_: Exception) {
            emptyList()
        }
    }


    suspend fun update(collectionName: String, updater: Updater): Boolean =
        suspendCoroutine { continuation ->
            val collectionRef = Firebase.firestore.collection(collectionName)
            val docRef = collectionRef.document(updater.docID)
            docRef.update(
                updater.field,
                updater.value
            ).addOnSuccessListener {
                continuation.resume(true)
            }
                .addOnFailureListener {
                    continuation.resume(false)
                }
        }


    inline fun <reified T> asList(querySnapshot: QuerySnapshot): List<T> {
        return querySnapshot.documents
            .filter { it.exists() }
            .mapNotNull { it.toObject(T::class.java) }
    }
}
