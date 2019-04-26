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

package com.tailoredapps.androidutil.network.emptynetworkresponse

import com.tailoredapps.androidutil.network.NetworkUnavailableException
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

internal class EmptyNetworkResponseRxJava2CallAdapter(
    private val delegateAdapter: CallAdapter<Response<Void>, Observable<Response<Void>>>
) : CallAdapter<Response<Void>, Any> {

    override fun responseType(): Type = Void::class.java

    override fun adapt(call: Call<Response<Void>>): Any {
        return delegateAdapter.adapt(call)
            .map<EmptyNetworkResponse> {
                if (it.isSuccessful) EmptyNetworkResponse.Success
                else EmptyNetworkResponse.ServerError(HttpException(it))
            }
            .onErrorResumeNext { throwable: Throwable ->
                when (throwable) {
                    is HttpException -> Observable.just(EmptyNetworkResponse.ServerError(throwable))
                    is IOException -> Observable.just(EmptyNetworkResponse.NetworkError(NetworkUnavailableException(throwable)))
                    else -> Observable.error(throwable)
                }
            }
            .singleOrError()
    }
}