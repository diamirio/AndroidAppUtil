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

package com.tailoredapps.androidutil.ui.extensions

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

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