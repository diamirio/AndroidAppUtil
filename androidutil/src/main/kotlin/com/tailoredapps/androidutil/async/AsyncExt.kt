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

import com.tailoredapps.androidutil.networkresponse.NetworkResponse
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Extension function that materializes an Observable and maps the resulting Notification to the [Async] type.
 */
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


/**
 * Extension function that maps a [NetworkResponse] to the [Async] type.
 */
fun <SuccessType : Any> Single<NetworkResponse<SuccessType>>.mapNetworkResponseToAsync(): Single<Async<SuccessType>> {
    return map {
        when (it) {
            is NetworkResponse.Success -> Async.Success(it.element)
            is NetworkResponse.ServerError -> Async.Error(it.error)
            is NetworkResponse.NetworkError -> Async.Error(it.error)
        }
    }
}