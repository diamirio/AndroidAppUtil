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

import androidx.annotation.CheckResult
import androidx.annotation.IdRes
import com.google.android.material.chip.ChipGroup
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer

/**
 * A [Consumer] that either checks the [Chip] with the [IdRes] inside the [ChipGroup] or clears the checked [Chip]
 * when the value in the [Consumer] is -1.
 */
@CheckResult
fun ChipGroup.checked(): Consumer<in Int> {
    return Consumer { if (it == -1) clearCheck() else check(it) }
}

/**
 * Returns an [Observable] that emits the [IdRes] of the checked [Chip] in a single selection [ChipGroup].
 * Only one listener can be attached to a [ChipGroup].
 */
@CheckResult
fun ChipGroup.checkedChanges(): Observable<Int> {
    return ChipGroupCheckedChangeObservable(this)
}

private class ChipGroupCheckedChangeObservable(
    private val view: ChipGroup
) : Observable<Int>() {

    override fun subscribeActual(observer: Observer<in Int>) {
        if (!checkSingleSelection(view, observer)) return
        val listener = Listener(view, observer)
        view.setOnCheckedChangeListener(listener)
        observer.onSubscribe(listener)
    }

    private class Listener(
        private val view: ChipGroup,
        private val observer: Observer<in Int>
    ) : MainThreadDisposable(), ChipGroup.OnCheckedChangeListener {
        private var lastChecked = -1

        override fun onCheckedChanged(chipGroup: ChipGroup, checkedId: Int) {
            if (!isDisposed && checkedId != lastChecked) {
                lastChecked = checkedId
                observer.onNext(checkedId)
            }
        }

        override fun onDispose() {
            view.setOnCheckedChangeListener(null)
        }
    }
}

private fun checkSingleSelection(view: ChipGroup, observer: Observer<*>): Boolean {
    if (!view.isSingleSelection) {
        observer.onSubscribe(Disposables.empty())
        observer.onError(IllegalStateException("The view is not in single selection mode!"))
        return false
    }
    return true
}