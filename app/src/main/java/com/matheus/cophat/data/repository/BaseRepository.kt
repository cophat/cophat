package com.matheus.cophat.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.matheus.cophat.data.local.entity.Questionnaire
import com.matheus.cophat.data.presenter.QuestionnairePresenter
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

abstract class BaseRepository {

    abstract fun getDatabase(): DatabaseReference

    suspend fun <T> getDatabaseChild(child: FirebaseChild, dataType: Class<T>): List<T> =
        suspendCoroutine { d ->
            val children = ArrayList<T>()
            getDatabase().addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    d.resumeWithException(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        for (dataSnapshot in snapshot.child(child.pathName).children) {
                            dataSnapshot.getValue(dataType)?.let { children.add(it) }
                        }

                        d.resume(children)
                    } catch (e: Exception) {
                        d.resumeWithException(e)
                    }
                }
            })
        }

    suspend fun <T> getDatabaseChildHash(
        child: FirebaseChild,
        dataType: Class<T>
    ): HashMap<String, T> =
        suspendCoroutine { d ->
            val children = HashMap<String, T>()
            getDatabase().addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    d.resumeWithException(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        for (dataSnapshot in snapshot.child(child.pathName).children) {
                            dataSnapshot.getValue(dataType)?.let { value ->
                                dataSnapshot.key?.let { key ->
                                    children[key] = value
                                }
                            }
                        }

                        d.resume(children)
                    } catch (e: Exception) {
                        d.resumeWithException(e)
                    }
                }
            })
        }

    suspend fun addChild(child: String, value: Any): Void? =
        getDatabase().child(child).push().setValue(value).await()

    suspend fun addChild(child: FirebaseChild, value: Any): Void? =
        getDatabase().child(child.pathName).push().setValue(value).await()

    suspend fun updateChild(child: FirebaseChild, key: String, updated: Any): Void? =
        suspendCoroutine { d ->
            val childUpdates = HashMap<String, Any>()
            childUpdates["/${child.pathName}/$key"] = updated

            getDatabase().updateChildren(childUpdates)
                .addOnCompleteListener { d.resume(null) }
                .addOnFailureListener { d.resumeWithException(it) }
        }

    suspend fun removeChild(child: FirebaseChild, key: String): Void? =
        suspendCoroutine { d ->
            val childUpdates = HashMap<String, Any?>()
            childUpdates["/${child.pathName}/$key"] = null

            getDatabase().updateChildren(childUpdates)
                .addOnCompleteListener { d.resume(null) }
                .addOnFailureListener { d.resumeWithException(it) }
        }

    suspend fun getQuestionnaireByFamilyId(familyId: String): QuestionnairePresenter? {
        return getDatabaseChildHash(FirebaseChild.QUESTIONNAIRES, Questionnaire::class.java)
            .filter { it.value.familyId == familyId }
            .map { (key, value) -> QuestionnairePresenter(value, key) }
            .firstOrNull()
    }
}