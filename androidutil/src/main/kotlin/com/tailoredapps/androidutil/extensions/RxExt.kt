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

package com.tailoredapps.androidutil.extensions

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
 * Converts a value to a Maybe that signals onComplete if te value is null, else it emits the value
 * in onSuccess.
 */
@CheckReturnValue
fun <T> T?.toMaybe(): Maybe<T> = Maybe.create { if (this == null) it.onComplete() else it.onSuccess(this) }

/**
 * Maps each element of the observable to a maybe which is either empty when the observable element
 * is null or emits a maybe success with the observable element.
 */
@CheckReturnValue
fun <S : Any, T : Any> Observable<S>.flatMapOptionalAsMaybe(mapper: (S) -> T?): Observable<T> =
    this.flatMapMaybe { mapper.invoke(it).toMaybe() }

/**
 * Converts a Completable to an Observable with a default completion value.
 */
@CheckReturnValue
fun <T : Any> Completable.toObservableDefault(completionValue: T): Observable<T> =
    toSingleDefault(completionValue).toObservable()


/**
 * Filters the item of the specified type T by mapping the Single to a Maybe with type R.
 */
@CheckReturnValue
inline fun <reified R : Any> Single<*>.ofType(): Maybe<R> =
    toObservable().ofType(R::class.java).firstElement()