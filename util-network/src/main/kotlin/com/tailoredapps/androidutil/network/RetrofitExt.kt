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

package com.tailoredapps.androidutil.network

import com.tailoredapps.androidutil.network.emptynetworkresponse.EmptyNetworkResponseRxJava2CallAdapterFactory
import com.tailoredapps.androidutil.network.networkresponse.NetworkResponseRxJava2CallAdapterFactory
import retrofit2.Retrofit

/**
 * Adds both [NetworkResponse] and [EmptyNetworkResponse] as call adapter types to retrofit instance.
 * Important: add this before the [retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory]
 */
fun Retrofit.Builder.addNetworkResponseCallAdapterFactories() {
    addCallAdapterFactory(NetworkResponseRxJava2CallAdapterFactory.create())
    addCallAdapterFactory(EmptyNetworkResponseRxJava2CallAdapterFactory.create())
}