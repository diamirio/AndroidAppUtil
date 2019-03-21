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

package com.tailoredapps.androidutil.optional

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.rxkotlin.ofType


/**
 * Filters items of type T if they are not null.
 */
fun <T : Any> Observable<out Optional<T>>.filterSome(): Observable<T> =
        ofType<Optional.Some<T>>().map { it.value }

/**
 * Filters items of type T if they are not null.
 */
fun <T : Any> Flowable<out Optional<T>>.filterSome(): Flowable<T> =
        ofType<Optional.Some<T>>().map { it.value }

/**
 * Filters item of type T if it is not null.
 */
fun <T : Any> Maybe<out Optional<T>>.filterSome(): Maybe<T> =
        ofType<Optional.Some<T>>().map { it.value }

/**
 * Filters item of type T if it is not null.
 */
fun <T : Any> Single<out Optional<T>>.filterSome(): Maybe<T> =
        ofType<Optional.Some<T>>().map { it.value }


/**
 * Filters items of type T if they are null.
 */
fun <T : Any> Observable<out Optional<T>>.filterNone(): Observable<Unit> =
        ofType<Optional.None>().map { Unit }

/**
 * Filters items of type T if they are null.
 */
fun <T : Any> Flowable<out Optional<T>>.filterNone(): Flowable<Unit> =
        ofType<Optional.None>().map { Unit }

/**
 * Filters item of type T if it is null.
 */
fun <T : Any> Maybe<out Optional<T>>.filterNone(): Maybe<Unit> =
        ofType<Optional.None>().map { Unit }

/**
 * Filters item of type T if it is null.
 */
fun <T : Any> Single<out Optional<T>>.filterNone(): Maybe<Unit> =
        ofType<Optional.None>().map { Unit }


/**
 * Filters the item of the specified type T by mapping the Single to a Maybe with type R.
 */
@CheckReturnValue
inline fun <reified R : Any> Single<*>.ofType(): Maybe<R> = filter { it is R }.cast(R::class.java)