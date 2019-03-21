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
 * See the License for the specific language governing permissionsState and
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
fun <C : Context> C.permissionState(of: Permission): PermissionState {
    return if (ContextCompat.checkSelfPermission(this, of.manifestPermission) == PackageManager.PERMISSION_DENIED) {
        if (this is Activity &&
            !ActivityCompat.shouldShowRequestPermissionRationale(this, of.manifestPermission)
        ) {
            PermissionState.NeverAskAgain(of)
        } else {
            PermissionState.Denied(of)
        }
    } else {
        PermissionState.Granted(of)
    }
}

/**
 * Queries the [PermissionState] of a [Permission].
 */
fun <F : Fragment> F.permissionState(of: Permission): PermissionState =
    requireContext().permissionState(of)

/**
 * Queries the [PermissionState] of this [Permission].
 */
fun Permission.queryState(context: Context): PermissionState =
    context.permissionState(this)

/**
 * Queries the [PermissionState] of this [Permission].
 */
fun Permission.queryState(fragment: Fragment): PermissionState =
    fragment.permissionState(this)

/**
 * Queries the [PermissionState] of multiple [Permission].
 */
fun <C : Context> C.permissionsState(vararg of: Permission): List<PermissionState> =
    of.map { permissionState(it) }

/**
 * Queries the [PermissionState] of multiple [Permission].
 */
fun <F : Fragment> F.permissionsState(vararg of: Permission): List<PermissionState> {
    return requireContext().permissionsState(*of)
}

/**
 * Queries the [PermissionState] of this list of [Permission].
 */
fun List<Permission>.queryStates(context: Context): List<PermissionState> =
    context.permissionsState(*this.toTypedArray())

/**
 * Queries the [PermissionState] of this list of [Permission].
 */
fun List<Permission>.queryStates(fragment: Fragment): List<PermissionState> =
    fragment.permissionsState(*this.toTypedArray())