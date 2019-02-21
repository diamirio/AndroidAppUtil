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

package com.tailoredapps.androidutil.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.lang.RuntimeException

/**
 * Queries the [PermissionState] of a [Permission].
 */
fun Context.permission(with: Permission): PermissionState {
    return when {
        ContextCompat.checkSelfPermission(this, with.manifestPermission) == PackageManager.PERMISSION_DENIED -> {
            if (this is Activity && !ActivityCompat.shouldShowRequestPermissionRationale(this, with.manifestPermission)) {
                PermissionState.NeverAskAgain(with)
            } else {
                PermissionState.Denied(with)
            }
        }
        else -> PermissionState.Granted(with)
    }
}

/**
 * Queries the [PermissionState] of a [Permission].
 */
fun Fragment.permission(with: Permission): PermissionState {
    val context = context ?: throw RuntimeException("Context must not be null!")
    return context.permission(with)
}

/**
 * Queries the [PermissionState] of this [Permission].
 */
fun Permission.query(context: Context): PermissionState = context.permission(this)

/**
 * Queries the [PermissionState] of this [Permission].
 */
fun Permission.query(fragment: Fragment): PermissionState = fragment.permission(this)

/**
 * Queries the [PermissionState] of multiple [Permission].
 */
fun Context.permissions(vararg with: Permission): List<PermissionState> {
    return with.map { permission(it) }
}

/**
 * Queries the [PermissionState] of multiple [Permission].
 */
fun Fragment.permissions(vararg with: Permission): List<PermissionState> {
    val context = context ?: throw RuntimeException("Context must not be null!")
    return context.permissions(*with)
}

/**
 * Queries the [PermissionState] of this list of [Permission].
 */
fun List<Permission>.query(context: Context): List<PermissionState> = context.permissions(*this.toTypedArray())

/**
 * Queries the [PermissionState] of this list of [Permission].
 */
fun List<Permission>.query(fragment: Fragment): List<PermissionState> = fragment.permissions(*this.toTypedArray())