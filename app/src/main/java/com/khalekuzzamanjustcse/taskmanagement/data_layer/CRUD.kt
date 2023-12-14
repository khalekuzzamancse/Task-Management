package com.khalekuzzamanjustcse.taskmanagement.data_layer

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class Demo @JvmOverloads constructor(
    @DocumentId val id: String = "",
    val name: String = "",
)




class DatabaseCollectionReader(
    collection: String,
) {
    private val collectionRef = Firebase.firestore.collection(collection)
    suspend fun <T> readObservable(
        classType: Class<T>,
        where: Filter? = null
    ): Flow<List<T>> {
        return callbackFlow {
            val callback = EventListener<QuerySnapshot> { value, _ ->
                value?.let { response ->
                    val result = response.toObjects(classType)
                    if (result.isNotEmpty())
                        trySend(result)
                }
            }

            val listOfDocumentSnapshot = where?.let { collectionRef.where(it) } ?: collectionRef
            val registration = listOfDocumentSnapshot.addSnapshotListener(callback)
            awaitClose { registration.remove() }
        }
    }



}

class DatabaseDocumentReader(
    private val collection: String,
    private val docId: String
) {
    private val collectionRef = Firebase.firestore.collection(collection)
    private val documentReference = collectionRef.document(docId)
    suspend fun <T> readOneTime(classType: Class<T>): T? {
        return try {
            val documentSnapshot = collectionRef.document(docId)
                .get()
                .await()
            if (documentSnapshot.exists())
                documentSnapshot.toObject(classType)
            else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun <T> readObservable(classType: Class<T>): Flow<T> {
        return callbackFlow {
            val callback = EventListener<DocumentSnapshot> { document, _ ->
                if (document != null) {
                    val data = document.toObject(classType)
                    if (data != null) {
                        trySend(data)
                    }
                }
            }
            val registration = documentReference.addSnapshotListener(callback)
            awaitClose { registration.remove() }
        }
    }


}

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

    suspend inline fun <reified T> read(collection: String, docId: String): T? {
        return try {
            val collectionRef = Firebase.firestore.collection(collection)
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
        collection: String,
        where: Filter? = null
    ): List<T> {
        val collectionRef = Firebase.firestore.collection(collection)
        return try {
            val querySnapshot = withContext(Dispatchers.IO) {
                val query = if (where != null)
                    collectionRef.where(where).get()
                else collectionRef.get()
                query.await()
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
