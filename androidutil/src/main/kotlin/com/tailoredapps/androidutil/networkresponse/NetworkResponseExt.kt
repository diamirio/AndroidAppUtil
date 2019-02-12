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

package com.tailoredapps.androidutil.networkresponse

import io.reactivex.Single

/**
 * Extension function that splits a NetworkResponse Single into an OnSucces or OnError emission.
 */
fun <SuccessType : Any> Single<NetworkResponse<SuccessType>>.splitNetworkResponse(): Single<SuccessType> {
    return flatMap {
        when (it) {
            is NetworkResponse.Success -> Single.just(it.element)
            is NetworkResponse.ServerError -> Single.error(it.error)
            is NetworkResponse.NetworkError -> Single.error(it.error)
        }
    }
}