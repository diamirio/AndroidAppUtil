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

package com.tailoredapps.androidutil.network.emptynetworkresponse

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue

/**
 * Extension function that splits an [EmptyNetworkResponse] [Single] into an OnComplete or OnError emission.
 */
@CheckReturnValue
fun Single<EmptyNetworkResponse>.split(): Completable {
    return flatMapCompletable {
        when (it) {
            is EmptyNetworkResponse.Success -> Completable.complete()
            is EmptyNetworkResponse.ServerError -> Completable.error(it.error)
            is EmptyNetworkResponse.NetworkError -> Completable.error(it.error)
        }
    }
}

/**
 * Extension function that splits a [EmptyNetworkResponse] [Observable] into an OnSuccess or OnError emission.
 */
@CheckReturnValue
fun Observable<EmptyNetworkResponse>.split(): Observable<Unit> {
    return flatMap {
        when (it) {
            is EmptyNetworkResponse.Success -> Observable.just(Unit)
            is EmptyNetworkResponse.ServerError -> Observable.error(it.error)
            is EmptyNetworkResponse.NetworkError -> Observable.error(it.error)
        }
    }
}

/**
 * Extension function that folds a [EmptyNetworkResponse] to a wrapped type that has an success and an error state.
 */
@CheckReturnValue
fun <Wrapper : Any> Single<EmptyNetworkResponse>.fold(
    successCreator: () -> Wrapper,
    errorCreator: (error: Throwable) -> Wrapper
): Single<out Wrapper> {
    return map {
        when (it) {
            is EmptyNetworkResponse.Success -> successCreator()
            is EmptyNetworkResponse.ServerError -> errorCreator(it.error)
            is EmptyNetworkResponse.NetworkError -> errorCreator(it.error)
        }
    }
}

/**
 * Extension function that folds a [EmptyNetworkResponse] to a wrapped type that has an success and an error state.
 */
@CheckReturnValue
fun <Wrapper : Any> Observable<EmptyNetworkResponse>.fold(
    successCreator: () -> Wrapper,
    errorCreator: (error: Throwable) -> Wrapper
): Observable<out Wrapper> {
    return map {
        when (it) {
            is EmptyNetworkResponse.Success -> successCreator()
            is EmptyNetworkResponse.ServerError -> errorCreator(it.error)
            is EmptyNetworkResponse.NetworkError -> errorCreator(it.error)
        }
    }
}