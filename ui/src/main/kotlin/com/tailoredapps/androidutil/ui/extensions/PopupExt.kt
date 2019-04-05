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

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.core.view.children
import androidx.core.view.forEach
import io.reactivex.Single

sealed class RxPopupAction {
    object Cancelled : RxPopupAction()
    data class Selected(val index: Int, val menuItem: MenuItem) : RxPopupAction()
}

/**
 * Convenience function to show a popup menu for a View.
 */
fun <T : View> T.rxPopup(@MenuRes menuId: Int, itemsAdapter: ((MenuItem) -> Unit)? = null): Single<RxPopupAction> =
    Single.create { emitter ->
        val menu = PopupMenu(context, this).apply {
            menuInflater.inflate(menuId, menu)
            itemsAdapter?.let { adapter -> menu.forEach { adapter.invoke(it) } }
            setOnMenuItemClickListener {
                emitter.onSuccess(RxPopupAction.Selected(menu.children.indexOf(it), it))
                true
            }
            setOnDismissListener { emitter.onSuccess(RxPopupAction.Cancelled) }
        }

        emitter.setCancellable { menu.dismiss() }
        menu.show()
    }