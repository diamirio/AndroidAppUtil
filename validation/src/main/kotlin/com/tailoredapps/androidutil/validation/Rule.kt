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

/**
 * Extendable set of Rules that can be used to validate a property.
 */
interface Rule<T : Any> {
    @get:StringRes
    val errorMessage: Int

    fun validate(input: T?): Boolean
}

class NotNullRule<T : Any>(@StringRes override val errorMessage: Int) :
    Rule<T> {
    override fun validate(input: T?) = input != null
}

class NotEmptyRule(@StringRes override val errorMessage: Int) : Rule<String> {
    override fun validate(input: String?) = input != null && input.isNotEmpty()
}

class EqualRule(private val other: () -> String?, @StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?) = input == other.invoke()
}

open class RegexRule(private val pattern: String, @StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?) = input != null && pattern.toRegex().matches(input)
}

class MinLengthRule(private val length: Int, @StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?): Boolean = input != null && input.length >= length
}

class MaxLengthRule(private val length: Int, @StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?): Boolean = input != null && input.length <= length
}

class AtLeastOneUpperCaseLetterRule(@StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?) = input != null && input.any { it.isUpperCase() }
}

class AtLeastOneLowerCaseLetterRule(@StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?) = input != null && input.any { it.isLowerCase() }
}

class AtLeastOneDigitRule(@StringRes override val errorMessage: Int) :
    Rule<String> {
    override fun validate(input: String?) = input != null && input.any { it.isDigit() }
}

class EmailRule(@StringRes errorMessage: Int) : RegexRule(
    "(?:[\\p{L}0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[\\p{L}0-9!#$%\\&'*+/=?\\^_`{|}" +
            "~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\" +
            "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[\\p{L}0-9](?:[a-" +
            "z0-9-]*[\\p{L}0-9])?\\.)+[\\p{L}0-9](?:[\\p{L}0-9-]*[\\p{L}0-9])?|\\[(?:(?:25[0-5" +
            "]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-" +
            "9][0-9]?|[\\p{L}0-9-]*[\\p{L}0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21" +
            "-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
    errorMessage
)