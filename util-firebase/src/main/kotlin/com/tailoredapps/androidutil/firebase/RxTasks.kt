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

package com.tailoredapps.androidutil.firebase

import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * Use the [RxTasks] to Rx-ify the [Task] API (https://developers.google.com/android/guides/tasks).
 * e.g.
 *
 * val reference: StorageReference
 * RxTasks.completable { reference.delete() }
 *
 * or
 *
 * val data: Intent
 * RxTasks.single { GoogleSignIn.getSignedInAccountFromIntent(data) }
 */
object RxTasks {
    inline fun <T> single(crossinline taskCreator: () -> Task<T>): Single<T> =
        Single.create { taskCreator().singleEmitter(it) }

    inline fun <T> completable(crossinline taskCreator: () -> Task<T>): Completable =
        Completable.create { taskCreator().completableEmitter(it) }

    inline fun <T> maybe(crossinline taskCreator: () -> Task<T>): Maybe<T> =
        Maybe.create { taskCreator().maybeEmitter(it) }

    @PublishedApi
    internal fun <T> Task<T>.singleEmitter(emitter: SingleEmitter<T>) {
        addOnSuccessListener(emitter::onSuccess)
        addOnFailureListener(emitter::onError)
    }

    @PublishedApi
    internal fun <T> Task<T>.maybeEmitter(emitter: MaybeEmitter<T>) {
        addOnSuccessListener {
            if (it == null) emitter.onComplete()
            else emitter.onSuccess(it)
        }
        addOnFailureListener(emitter::onError)
    }

    @PublishedApi
    internal fun <T> Task<T>.completableEmitter(emitter: CompletableEmitter) {
        addOnSuccessListener { emitter.onComplete() }
        addOnFailureListener(emitter::onError)
    }
}

fun <T> Task<T>.asSingle(): Single<T> =
    Single.create { emitter ->
        addOnSuccessListener(emitter::onSuccess)
        addOnFailureListener(emitter::onError)
    }

fun <T> Task<T>.asCompletable(): Completable =
    Completable.create { emitter ->
        addOnSuccessListener { emitter.onComplete() }
        addOnFailureListener(emitter::onError)
    }

fun <T> Task<T>.asMaybe(): Maybe<T> =
    Maybe.create { emitter ->
        addOnSuccessListener {
            if (it == null) emitter.onComplete()
            else emitter.onSuccess(it)
        }
        addOnFailureListener(emitter::onError)
    }
