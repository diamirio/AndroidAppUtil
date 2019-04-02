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

package com.tailoredapps.androidutil.ui.rxviews

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Returns an Observable that emits BottomSheetBehaviorEvent's of the BottomSheet.
 * Watch out, only one Event Listener can be set on a BottomSheet!
 */
fun <T : View> T.bottomSheetEvents(): Observable<BottomSheetBehaviorEvent> {
    return BottomSheetBehaviorSlideEventObservable(this)
}

sealed class BottomSheetBehaviorEvent(val bottomSheetView: View) {
    class Slide(bottomSheetView: View, val slideOffset: Float) : BottomSheetBehaviorEvent(bottomSheetView)
    class State(bottomSheetView: View, val newState: Int) : BottomSheetBehaviorEvent(bottomSheetView)
}

internal class BottomSheetBehaviorSlideEventObservable(private val view: View) :
    Observable<BottomSheetBehaviorEvent>() {
    override fun subscribeActual(observer: Observer<in BottomSheetBehaviorEvent>) {
        if (view.layoutParams !is CoordinatorLayout.LayoutParams) {
            throw IllegalArgumentException("The view is not in a Coordinator Layout.")
        }
        val params = view.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as BottomSheetBehavior<*>?
            ?: throw IllegalStateException("There's no behavior set on this view.")
        val listener = Listener(behavior, observer)
        observer.onSubscribe(listener)
        behavior.setBottomSheetCallback(listener.bottomSheetCallback)
    }

    private inner class Listener(
        private val bottomSheetBehavior: BottomSheetBehavior<*>,
        observer: Observer<in BottomSheetBehaviorEvent>
    ) : MainThreadDisposable() {
        internal val bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback

        init {
            this.bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    observer.onNext(
                        BottomSheetBehaviorEvent.State(
                            bottomSheet,
                            newState
                        )
                    )
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    observer.onNext(
                        BottomSheetBehaviorEvent.Slide(
                            bottomSheet,
                            slideOffset
                        )
                    )
                }
            }
        }

        override fun onDispose() {
            bottomSheetBehavior.setBottomSheetCallback(null)
        }
    }
}