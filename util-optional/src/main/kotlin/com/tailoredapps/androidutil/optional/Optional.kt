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

package com.tailoredapps.androidutil.optional

/**
 * A wrapper for nullable types. Mostly needed for Kotlin compliance with Java APIs such as RxJava.
 */
sealed class Optional<out Type : Any> {
    open operator fun invoke(): Type? = null

    object None : Optional<Nothing>()

    data class Some<out Type : Any>(val value: Type) : Optional<Type>() {
        override fun invoke(): Type = value
    }
}

/**
 * Wraps a nullable instance of T to an [Optional].
 */
val <T : Any> T?.asOptional: Optional<T>
    get() = if (this == null) Optional.None else Optional.Some(this)