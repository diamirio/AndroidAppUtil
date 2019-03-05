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

import android.view.View
import android.view.ViewTreeObserver
import com.google.android.material.appbar.AppBarLayout


/**
 * Lifts the AppBarLayout according to the scroll of a View.
 *
 * canScrollVertically(-1) checks whether a view can no longer scroll upwards.
 *
 * A reference to the listener is kept as a view tag and must be removed again using
 * [removeLiftWith] to avoid memory leaks.
 *
 */
fun <T : View> AppBarLayout.liftWith(view: T) {
    ViewTreeObserver.OnScrollChangedListener { setLifted(view.canScrollVertically(-1)) }
        .also { listener ->
            view.viewTreeObserver.addOnScrollChangedListener(listener)
            view.tag = listener
        }
}

/**
 * Remove the previously set scroll listener from the view to avoid memory leaks
 */
fun <T : View> AppBarLayout.removeLiftWith(view: T) {
    (view.tag as? ViewTreeObserver.OnScrollChangedListener)?.let { view.viewTreeObserver.removeOnScrollChangedListener(it) }
}