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

import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A [CallAdapter.Factory] which allows [NetworkResponse] objects to be returned from RxJava
 * streams.
 *
 * Adding this class to [Retrofit] allows you to return [Observable], [Flowable], [Single], or
 * [Maybe] types parameterized with [NetworkResponse] from service methods.
 *
 * Note: This adapter must be registered before an adapter that is capable of adapting RxJava
 * streams.
 */
class NetworkResponseRxJava2CallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        fun create() = NetworkResponseRxJava2CallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)

        val isFlowable = rawType === Flowable::class.java
        val isSingle = rawType === Single::class.java
        val isMaybe = rawType === Maybe::class.java
        if (rawType !== Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("${rawType.simpleName} return type must be parameterized as ${rawType.simpleName}<Foo> or ${rawType.simpleName}<? extends Foo>")
        }

        val observableEmissionType = getParameterUpperBound(0, returnType)
        if (getRawType(observableEmissionType) != NetworkResponse::class.java) {
            return null
        }

        if (observableEmissionType !is ParameterizedType) {
            throw IllegalStateException("NetworkResponse must be parameterized as NetworkResponse<SuccessBody, ErrorBody>")
        }

        val successBodyType = getParameterUpperBound(0, observableEmissionType)
        val delegateType = TypeToken.getParameterized(Observable::class.java, successBodyType).type
        val delegateAdapter = retrofit.nextCallAdapter(this, delegateType, annotations)

        @Suppress("UNCHECKED_CAST") // Type of delegateAdapter is not known at compile time.
        return NetworkResponseRxJava2CallAdapter(
            successBodyType,
            delegateAdapter as CallAdapter<Any, Observable<Any>>,
            isFlowable,
            isSingle,
            isMaybe
        )
    }
}
