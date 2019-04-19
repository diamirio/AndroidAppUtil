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

import com.tailoredapps.androidutil.network.NetworkUnavailableException
import retrofit2.HttpException

/**
 * Sealed class that wraps success or error result of a network call. Use this with [Single], instead
 * of [Completable].
 */
sealed class EmptyNetworkResponse {
    object Success : EmptyNetworkResponse()
    data class ServerError(val error: HttpException) : EmptyNetworkResponse()
    data class NetworkError(val error: NetworkUnavailableException) : EmptyNetworkResponse()
}
