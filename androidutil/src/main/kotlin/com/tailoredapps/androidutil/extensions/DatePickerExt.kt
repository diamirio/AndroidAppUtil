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

import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.threeten.bp.LocalDate


sealed class RxDatePickerAction {
    object Cancelled : RxDatePickerAction()
    data class Selected(val date: LocalDate) : RxDatePickerAction()
}

/**
 * Picks a org.threeten.bp.LocalDate using the default DatePicker.
 */
fun FragmentActivity.rxDatePicker(preset: LocalDate? = null): Single<RxDatePickerAction> {
    return Single
        .create { emitter: SingleEmitter<RxDatePickerAction> ->
            val date = preset ?: LocalDate.now()

            val dialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    emitter.onSuccess(RxDatePickerAction.Selected(LocalDate.of(year, month + 1, dayOfMonth)))
                },
                date.year,
                date.month.value - 1,
                date.dayOfMonth
            ).apply {
                setOnCancelListener { emitter.onSuccess(RxDatePickerAction.Cancelled) }
                show()
            }

            emitter.setCancellable { dialog.cancel() }
        }
        .subscribeOn(AndroidSchedulers.mainThread())
}

/**
 * Picks a org.threeten.bp.LocalDate using the default DatePicker.
 */
fun Fragment.rxDatePicker(): Single<RxDatePickerAction> {
    val activity = this.activity
        ?: throw RuntimeException("No Activity attached to Fragment. Cannot show Dialog.")
    return activity.rxDatePicker()
}