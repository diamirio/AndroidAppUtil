/**
 * Copyright 2019 Coinbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.tailoredapps.androidutil.network.networkresponse

import com.tailoredapps.androidutil.network.NetworkUnavailableException
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import java.io.IOException
import java.lang.reflect.Type

internal class NetworkResponseRxJava2CallAdapter<SuccessType : Any>(
    private val successBodyType: Type,
    private val delegateAdapter: CallAdapter<SuccessType, Observable<SuccessType>>,
    private val isFlowable: Boolean,
    private val isSingle: Boolean,
    private val isMaybe: Boolean
) : CallAdapter<SuccessType, Any> {

    override fun responseType(): Type = successBodyType

    override fun adapt(call: Call<SuccessType>): Any =
        delegateAdapter.adapt(call)
            .map<NetworkResponse<SuccessType>> { NetworkResponse.Success(it) }
            .onErrorResumeNext { throwable: Throwable ->
                when (throwable) {
                    is HttpException -> Observable.just(NetworkResponse.ServerError(throwable))
                    is IOException -> Observable.just(NetworkResponse.NetworkError(NetworkUnavailableException(throwable)))
                    else -> Observable.error(throwable)
                }
            }.let {
                when {
                    isFlowable -> it.toFlowable(BackpressureStrategy.LATEST)
                    isSingle -> it.singleOrError()
                    isMaybe -> it.singleElement()
                    else -> it
                }
            }
}