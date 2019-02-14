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

package com.tailoredapps.androidutil.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Convenience function to show a toast in a Fragment.
 */
fun Fragment.toast(@StringRes titleRes: Int, duration: Int = Toast.LENGTH_LONG): Toast? =
    context?.toast(getString(titleRes), duration)

/**
 * Convenience function to show a toast in a Fragment.
 */
fun Fragment.toast(title: String, duration: Int = Toast.LENGTH_LONG): Toast? =
    context?.toast(title, duration)

/**
 * Convenience function to show a toast from a Context.
 */
fun Context.toast(@StringRes titleRes: Int, duration: Int = Toast.LENGTH_LONG): Toast =
    toast(getString(titleRes), duration)

/**
 * Convenience function to show a toast from a Context.
 */
fun Context.toast(title: String, duration: Int = Toast.LENGTH_LONG): Toast {
    val toastDuration =
        if (duration == Toast.LENGTH_LONG || duration == Toast.LENGTH_SHORT) duration else Toast.LENGTH_SHORT
    return Toast.makeText(this, title, toastDuration).apply { show() }
}


/**
 * Convenience function to show a snackbar for a View.
 */
fun View.snack(
    @StringRes titleRes: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    @StringRes actionTextRes: Int? = null,
    action: (() -> Unit)? = null
): Snackbar = snack(context.getString(titleRes), duration, actionTextRes?.let(context::getString), action)

/**
 * Convenience function to show a snackbar for a View.
 */
fun View.snack(
    title: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    actionText: CharSequence? = null,
    action: (() -> Unit)? = null
): Snackbar {
    val snackDuration = when (duration) {
        Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE -> duration
        else -> Snackbar.LENGTH_LONG
    }
    return if (actionText != null && action != null) {
        Snackbar.make(this, title, snackDuration).setAction(actionText) { action.invoke() }.apply { show() }
    } else {
        Snackbar.make(this, title, snackDuration).apply { show() }
    }
}
