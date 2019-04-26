/*
 * Copyright 2019 Florian Schuster.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tailoredapps.androidutil.firebase.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.tailoredapps.androidutil.firebase.RxTasks
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.Flowables

/**
 * Converts a [DocumentSnapshot] into a [FirestoreObject] T.
 */
inline fun <reified T : FirestoreObject> DocumentSnapshot.localObject(): T? =
    toObject(T::class.java)?.apply { this.id = getId() }

/**
 * Converts a [QuerySnapshot] into a [FirestoreObject] list. Only items that can be converted are
 * added to the list.
 */
inline fun <reified T : FirestoreObject> QuerySnapshot.localObjects(): List<T> =
    if (this.isEmpty) emptyList() else documents.mapNotNull { it.localObject<T>() }

/**
 * Creates a Firestore document in a [CollectionReference] from the [objectToCreate]. If the [objectToCreate]
 * has an id, this id is uesd for the Docuemnt, else a random id is chosen.
 */
fun <T : FirestoreObject> CollectionReference.createObject(objectToCreate: T): Completable =
    RxTasks.completable {
        if (objectToCreate.id.isNotEmpty()) {
            document(objectToCreate.id).set(objectToCreate)
        } else {
            document().set(objectToCreate)
        }
    }

/**
 * Updates a document in the Firestore database.
 */
fun <T : FirestoreObject> DocumentReference.updateObject(value: T): Completable =
    RxTasks.completable { set(value, SetOptions.merge()) }

/**
 * Updates a [value] with a given [key] of a document in the Firestore database.
 */
fun DocumentReference.updateField(key: String, value: Any): Completable =
    RxTasks.completable { set(mapOf(key to value), SetOptions.merge()) }

/**
 * Returns a [Single] that emits the [FirestoreObject] of the firestore document or a [NoSuchElementException] if
 * it does not exist.
 */
inline fun <reified T : FirestoreObject> DocumentReference.localObject(): Single<T> =
    RxTasks.single(::get).map { if (!it.exists()) throw NoSuchElementException() else it.localObject<T>() }

/**
 * Returns a [Maybe] that emits the [FirestoreObject] of the firestore document or a completes if it does not exist.
 */
inline fun <reified T : FirestoreObject> DocumentReference.localObjectMaybe(): Maybe<T> =
    RxTasks.single(::get).flatMapMaybe { if (!it.exists()) Maybe.empty() else Maybe.just(it.localObject<T>()) }

/**
 * Returns a [Single] that emits the [FirestoreObject] list of the results of the Firestore query or
 * an empty list if the objects do not exist.
 */
inline fun <reified T : FirestoreObject> Query.localObjectList(): Single<List<T>> =
    RxTasks.single(::get).map { it.localObjects<T>() }

/**
 * Returns a [Flowable] that emits the [FirestoreObject] of the firestore document, everytime that it is changed
 * in the database.
 *
 * @param errorOnNoElement Boolean Whether the [Flowable] should signal an error when the Firestore document
 * does not exist or cannot be parsed.
 */
inline fun <reified T : FirestoreObject> DocumentReference.liveLocalObject(
    errorOnNoElement: Boolean = false
): Flowable<T> =
    Flowables.create(BackpressureStrategy.LATEST) { emitter: FlowableEmitter<T> ->
        val listener = addSnapshotListener { snapshot, error ->
            val element: T? = snapshot?.localObject()
            when {
                error != null -> emitter.onError(error)
                element != null -> emitter.onNext(element)
                errorOnNoElement -> emitter.onError(NoSuchElementException())
            }
        }
        emitter.setCancellable(listener::remove)
    }

/**
 * Returns a [Flowable] that emits the [FirestoreObject] list of the result of the firestore query, every
 * time that objects within the query range are changed in the database.
 *
 * @param errorOnNoElements Boolean Whether the [Flowable] should signal an error when the Firestore document
 * does not exist or cannot be parsed.
 */
inline fun <reified T : FirestoreObject> Query.liveLocalObjectList(
    errorOnNoElements: Boolean = false
): Flowable<List<T>> =
    Flowables.create(BackpressureStrategy.LATEST) { emitter: FlowableEmitter<List<T>> ->
        val listener = addSnapshotListener { snapshot, error ->
            val elements: List<T>? = snapshot?.localObjects()
            when {
                error != null -> emitter.onError(error)
                errorOnNoElements && (elements == null || elements.isEmpty()) -> {
                    emitter.onError(NoSuchElementException())
                }
                elements != null -> emitter.onNext(elements)
            }
        }
        emitter.setCancellable(listener::remove)
    }