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

package com.tailoredapps.androidutil.network.networkresponse

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue

/**
 * Extension function that splits a [NetworkResponse] [Single] into an OnSuccess or OnError emission.
 */
@CheckReturnValue
fun <SuccessType : Any> Single<NetworkResponse<SuccessType>>.split(): Single<out SuccessType> {
    return flatMap {
        when (it) {
            is NetworkResponse.Success -> Single.just(it.element)
            is NetworkResponse.ServerError -> Single.error(it.error)
            is NetworkResponse.NetworkError -> Single.error(it.error)
        }
    }
}

/**
 * Extension function that splits a [NetworkResponse] [Observable] into an OnSuccess or OnError emission.
 */
@CheckReturnValue
fun <SuccessType : Any> Observable<NetworkResponse<SuccessType>>.split(): Observable<out SuccessType> {
    return flatMap {
        when (it) {
            is NetworkResponse.Success -> Observable.just(it.element)
            is NetworkResponse.ServerError -> Observable.error(it.error)
            is NetworkResponse.NetworkError -> Observable.error(it.error)
        }
    }
}

/**
 * Extension function that folds a [NetworkResponse] to a wrapped type that has an success and an error state.
 */
@CheckReturnValue
@Deprecated("Renamed function to reflect it's actual functionality", replaceWith = ReplaceWith("this.wrap(successCreator, errorCreator)"))
fun <SuccessType : Any, Wrapper : Any> Single<NetworkResponse<SuccessType>>.fold(
    successCreator: (element: SuccessType) -> Wrapper,
    errorCreator: (error: Throwable) -> Wrapper
): Single<out Wrapper> {
    return map {
        when (it) {
            is NetworkResponse.Success -> successCreator(it.element)
            is NetworkResponse.ServerError -> errorCreator(it.error)
            is NetworkResponse.NetworkError -> errorCreator(it.error)
        }
    }
}

/**
 * Extension function that maps a [NetworkResponse] to a wrapped type that has an success and an error state.
 */
@CheckReturnValue
fun <SuccessType : Any, Wrapper : Any> Single<NetworkResponse<SuccessType>>.wrap(
    successCreator: (element: SuccessType) -> Wrapper,
    errorCreator: (error: Throwable) -> Wrapper
): Single<out Wrapper> {
    return map {
        when (it) {
            is NetworkResponse.Success -> successCreator(it.element)
            is NetworkResponse.ServerError -> errorCreator(it.error)
            is NetworkResponse.NetworkError -> errorCreator(it.error)
        }
    }
}

/**
 * Extension function that folds a [NetworkResponse] to a wrapped type that has an success and an error state.
 */
@CheckReturnValue
@Deprecated("Renamed function to reflect it's actual functionality", replaceWith = ReplaceWith("this.wrap(successCreator, errorCreator)"))
fun <SuccessType : Any, Wrapper : Any> Observable<NetworkResponse<SuccessType>>.fold(
    successCreator: (element: SuccessType) -> Wrapper,
    errorCreator: (error: Throwable) -> Wrapper
): Observable<out Wrapper> {
    return map {
        when (it) {
            is NetworkResponse.Success -> successCreator(it.element)
            is NetworkResponse.ServerError -> errorCreator(it.error)
            is NetworkResponse.NetworkError -> errorCreator(it.error)
        }
    }
}

/**
 * Extension function that maps a [NetworkResponse] to a wrapped type that has an success and an error state.
 */
@CheckReturnValue
fun <SuccessType : Any, Wrapper : Any> Observable<NetworkResponse<SuccessType>>.wrap(
    successCreator: (element: SuccessType) -> Wrapper,
    errorCreator: (error: Throwable) -> Wrapper
): Observable<out Wrapper> {
    return map {
        when (it) {
            is NetworkResponse.Success -> successCreator(it.element)
            is NetworkResponse.ServerError -> errorCreator(it.error)
            is NetworkResponse.NetworkError -> errorCreator(it.error)
        }
    }
}