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

package com.tailoredapps.androidutil.core.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.schedulers.Schedulers


/**
 * Observes this on the Android main thread.
 */
@CheckReturnValue
fun <T> Observable<T>.observeOnMain(): Observable<T> = observeOn(AndroidSchedulers.mainThread())

/**
 * Observes this on the Android main thread.
 */
@CheckReturnValue
fun Completable.observeOnMain(): Completable = observeOn(AndroidSchedulers.mainThread())

/**
 * Observes this on the Android main thread.
 */
@CheckReturnValue
fun <T> Flowable<T>.observeOnMain(): Flowable<T> = observeOn(AndroidSchedulers.mainThread())

/**
 * Observes this on the Android main thread.
 */
@CheckReturnValue
fun <T> Single<T>.observeOnMain(): Single<T> = observeOn(AndroidSchedulers.mainThread())

/**
 * Observes this on the Android main thread.
 */
@CheckReturnValue
fun <T> Maybe<T>.observeOnMain(): Maybe<T> = observeOn(AndroidSchedulers.mainThread())

/**
 * Subscribes this on the IO scheduler thread.
 */
@CheckReturnValue
fun <T> Observable<T>.subscribeOnIO(): Observable<T> = subscribeOn(Schedulers.io())

/**
 * Subscribes this on the IO scheduler thread.
 */
@CheckReturnValue
fun Completable.subscribeOnIO(): Completable = subscribeOn(Schedulers.io())

/**
 * Subscribes this on the IO scheduler thread.
 */
@CheckReturnValue
fun <T> Flowable<T>.subscribeOnIO(): Flowable<T> = subscribeOn(Schedulers.io())

/**
 * Subscribes this on the IO scheduler thread.
 */
@CheckReturnValue
fun <T> Single<T>.subscribeOnIO(): Single<T> = subscribeOn(Schedulers.io())

/**
 * Subscribes this on the IO scheduler thread.
 */
@CheckReturnValue
fun <T> Maybe<T>.subscribeOnIO(): Maybe<T> = subscribeOn(Schedulers.io())


/**
 * Converts a Completable to an Observable with a default completion value.
 */
@CheckReturnValue
fun <T : Any> Completable.toObservableDefault(completionValue: T): Observable<T> =
    toSingleDefault(completionValue).toObservable()


/**
 * Creates an Observable of [T]
 */
val <T : Any> T.observable: Observable<T>
    get() = Observable.just(this)