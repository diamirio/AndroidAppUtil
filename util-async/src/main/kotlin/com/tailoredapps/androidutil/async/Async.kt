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

/**
 * Sealed class that represents an asynchronous load of a resource.
 */
sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean) {
    open operator fun invoke(): T? = null

    object Uninitialized : Async<Nothing>(false, true)
    object Loading : Async<Nothing>(false, false)
    data class Error(val error: Throwable) : Async<Nothing>(true, true)
    data class Success<out T>(val element: T) : Async<T>(true, false) {
        override operator fun invoke(): T = element
    }

    val initialized: Boolean
        get() = this !is Uninitialized

    val loading: Boolean
        get() = this is Loading

    companion object {
        fun emptySuccess(): Success<Unit> = Success(Unit)
        fun <T : Any> success(element: T): Success<T> = Success(element)
        fun <T : Throwable> error(error: T): Error = Error(error)
    }
}