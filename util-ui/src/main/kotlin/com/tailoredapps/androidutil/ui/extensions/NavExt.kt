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

package com.tailoredapps.androidutil.ui.extensions

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Fills an intent with extra parameters.
 */
fun <I : Intent> I.extras(vararg params: Pair<String, Any>): I = apply { putExtras(bundleOf(*params)) }

/**
 * Lazily retrieves an extra from an the activity intent.
 */
inline fun <A : Activity, reified T : Any?> A.extra(key: String, defaultValue: T? = null): Lazy<T> = lazy {
    if (defaultValue == null) intent.extras?.get(key) as T
    else intent.extras?.get(key) as? T ?: defaultValue
}

/**
 * Fills the Fragment arguments with with parameters.
 */
fun <F : Fragment> F.args(vararg params: Pair<String, Any>): F = apply { arguments = bundleOf(*params) }

/**
 * Lazily retrieves an argument from the fragment arguments.
 */
inline fun <F : Fragment, reified T : Any?> F.argument(key: String, defaultValue: T? = null): Lazy<T> = lazy {
    if (defaultValue == null) arguments?.get(key) as T
    else arguments?.get(key) as? T ?: defaultValue
}

/**
 * Executes a FragmentManager transaction.
 */
inline fun <F : FragmentManager> F.transaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}