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

import android.app.Activity
import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.Calendar

// Resolves to: year, month (0-11), dayOfMonth (1-31)
data class RxDatePickerDay(val year: Int, val month: Int, val dayOfMonth: Int)

sealed class RxDatePickerAction {
    object Cancelled : RxDatePickerAction()
    data class Selected(val day: RxDatePickerDay) : RxDatePickerAction()
}

/**
 * Picks a Date using the default DatePicker.
 */
fun <T : Activity> T.rxDatePicker(preset: RxDatePickerDay? = null): Single<RxDatePickerAction> {
    return Single
        .create { emitter: SingleEmitter<RxDatePickerAction> ->
            val date = preset ?: Calendar.getInstance().let {
                RxDatePickerDay(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH),
                    it.get(Calendar.DAY_OF_MONTH)
                )
            }

            val dialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    emitter.onSuccess(
                        RxDatePickerAction.Selected(
                            RxDatePickerDay(
                                year,
                                month,
                                dayOfMonth
                            )
                        )
                    )
                },
                date.year, date.month, date.dayOfMonth
            ).apply {
                setOnCancelListener { emitter.onSuccess(RxDatePickerAction.Cancelled) }
                show()
            }

            emitter.setCancellable { dialog.cancel() }
        }
        .subscribeOn(AndroidSchedulers.mainThread())
}

/**
 * Picks a Date using the default DatePicker.
 */
fun <T : Fragment> T.rxDatePicker(preset: RxDatePickerDay? = null): Single<RxDatePickerAction> =
    requireActivity().rxDatePicker(preset)