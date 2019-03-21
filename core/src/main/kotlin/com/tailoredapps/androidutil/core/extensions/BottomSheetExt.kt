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

package com.tailoredapps.androidutil.core.extensions

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior


/**
 * Convenience function to create a [BottomSheetBehavior].
 */
val <T : View> T.asBottomSheet: BottomSheetBehavior<T>
    get() = BottomSheetBehavior.from(this)


/**
 * Convenience function to expand the BottomSheet.
 */
fun BottomSheetBehavior<*>.expand() {
    this.state = BottomSheetBehavior.STATE_EXPANDED
}

/**
 * Convenience function to collapse the BottomSheet.
 */
fun BottomSheetBehavior<*>.collapse() {
    this.state = BottomSheetBehavior.STATE_COLLAPSED
}