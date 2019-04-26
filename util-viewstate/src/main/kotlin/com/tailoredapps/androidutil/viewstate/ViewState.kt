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

package com.tailoredapps.androidutil.viewstate

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A property that is saved through [ViewState].
 * For supported property types, check [StoreAndRestoreProperty].
 */
interface ViewStateProperty<T : Any?> {
    operator fun provideDelegate(thisRef: Any, prop: KProperty<*>): ReadWriteProperty<Any, T>
}

/**
 * An Activity or Fragment that implements [ViewState], can easily store and restore properties from a bundle state.
 */
interface ViewState {
    /**
     * Use this for properties that should be saved in state.
     *
     * e.g. var someInteger: Int by viewStateProperty(420)
     */
    fun <T : Any?> viewStateProperty(defaultValue: T, key: String? = null): ViewStateProperty<T>

    /**
     * Restore state from [Bundle] state.
     *
     * e.g. in [Fragment]:
     * override fun onViewStateRestored(savedInstanceState: Bundle?) {
     *      super.onViewStateRestored(savedInstanceState)
     *      restoreStateFrom(savedInstanceState)
     * }
     */
    fun restoreStateFrom(state: Bundle?)

    /**
     * Store state in [Bundle] state.
     *
     * e.g. in [Fragment]:
     * override fun onSaveInstanceState(outState: Bundle) {
     *      super.onSaveInstanceState(outState)
     *      storeStateIn(outState)
     * }
     */
    fun storeStateIn(state: Bundle)
}

/**
 * Default [ViewState] Implementation. Can be used to implement [ViewState] by delegation.
 *
 * e.g.: class Activity : AppCompatActivity(), ViewState by VS()
 */
class VS : ViewState {
    private val properties: MutableList<StoreAndRestoreProperty<*>> by lazy { mutableListOf<StoreAndRestoreProperty<*>>() }

    override fun <T : Any?> viewStateProperty(defaultValue: T, key: String?): ViewStateProperty<T> =
        object : ViewStateProperty<T> {
            override operator fun provideDelegate(thisRef: Any, prop: KProperty<*>): ReadWriteProperty<Any, T> =
                StoreAndRestoreProperty(defaultValue, key ?: prop.name).also { properties.add(it) }
        }

    override fun restoreStateFrom(state: Bundle?) {
        if (state == null || properties.isEmpty()) return
        properties.forEach { it.restoreStateFrom(state) }
    }

    override fun storeStateIn(state: Bundle) {
        if (properties.isEmpty()) return
        properties.forEach { it.storeStateIn(state) }
    }
}