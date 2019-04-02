/*
 * Copyright 2019 Alexander Grafl & Florian Schuster.
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

package com.tailoredapps.androidutil.validation

import androidx.annotation.StringRes
import io.reactivex.Single

/**
 * A Result of a validation by a Validator.
 */
sealed class ValidationResult {
    object Unvalidated : ValidationResult()
    object Valid : ValidationResult()
    data class Invalid(@StringRes val errorMessage: Int) : ValidationResult()

    val valid: Boolean
        get() = this is Valid
}

/**
 * Helper property to ensure that all ValidationResults are valid.
 */
val Iterable<ValidationResult>.allValid: Boolean
    get() = all { it.valid }

/**
 * A Validator that is initialized with a set of Rules which are checked against for a given value with the
 * validate function.
 */
class Validator<T : Any>(rules: Iterable<Rule<T>>) {
    constructor(vararg rules: Rule<T>) : this(rules.toList())

    private val validationRules: LinkedHashSet<Rule<T>> = LinkedHashSet(rules.toList())

    fun validate(input: T?): ValidationResult {
        validationRules.forEach {
            if (!it.validate(input)) {
                return ValidationResult.Invalid(it.errorMessage)
            }
        }
        return ValidationResult.Valid
    }
}

/**
 * Validates an input with this Validator as a RxJava Single.
 */
fun <T : Any> Validator<T>.rxValidate(input: T?): Single<ValidationResult> =
    Single.fromCallable { validate(input) }