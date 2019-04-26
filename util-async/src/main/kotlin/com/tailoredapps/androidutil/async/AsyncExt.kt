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

package com.tailoredapps.androidutil.async

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.Experimental

/**
 * Extension function that materializes an [Single] and maps the resulting Notification to the [Async] type.
 */
@Experimental
@CheckReturnValue
fun <T : Any> Single<T>.mapToAsync(): Single<Async<T>> {
    return materialize()
        .flatMap {
            val value = it.value
            val error = it.error
            when {
                it.isOnNext && value != null -> Single.just(Async.Success(value))
                it.isOnError && error != null -> Single.just(Async.Error(error))
                else -> Single.never()
            }
        }
}

/**
 * Extension function that materializes an [Observable] and maps the resulting Notification to the [Async] type.
 */
@CheckReturnValue
fun <T : Any> Observable<T>.mapToAsync(): Observable<Async<T>> {
    return materialize()
        .flatMap {
            val value = it.value
            val error = it.error
            when {
                it.isOnNext && value != null -> Observable.just(Async.Success(value))
                it.isOnError && error != null -> Observable.just(Async.Error(error))
                else -> Observable.empty()
            }
        }
}