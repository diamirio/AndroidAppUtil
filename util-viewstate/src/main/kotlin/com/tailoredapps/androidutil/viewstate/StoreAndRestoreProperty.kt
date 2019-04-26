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
import android.os.Parcelable
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class StoreAndRestoreProperty<PropertyType : Any?>(
    private val defaultValue: PropertyType,
    private val key: String
) : ReadWriteProperty<Any, PropertyType> {

    private var value: PropertyType = defaultValue

    override fun getValue(thisRef: Any, property: KProperty<*>): PropertyType {
        return this.value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: PropertyType) {
        this.value = value
    }

    fun storeStateIn(bundle: Bundle) {
        when (value) {
            is Boolean -> bundle.putBoolean(key, value as Boolean)
            is BooleanArray -> bundle.putBooleanArray(key, value as BooleanArray)
            is Bundle -> bundle.putBundle(key, value as Bundle)
            is Byte -> bundle.putByte(key, value as Byte)
            is ByteArray -> bundle.putByteArray(key, value as ByteArray)
            is Char -> bundle.putChar(key, value as Char)
            is CharArray -> bundle.putCharArray(key, value as CharArray)
            is Double -> bundle.putDouble(key, value as Double)
            is DoubleArray -> bundle.putDoubleArray(key, value as DoubleArray)
            is Float -> bundle.putFloat(key, value as Float)
            is FloatArray -> bundle.putFloatArray(key, value as FloatArray)
            is Int -> bundle.putInt(key, value as Int)
            is IntArray -> bundle.putIntArray(key, value as IntArray)
            is Long -> bundle.putLong(key, value as Long)
            is LongArray -> bundle.putLongArray(key, value as LongArray)
            is Parcelable -> bundle.putParcelable(key, value as Parcelable)
            is Short -> bundle.putShort(key, value as Short)
            is ShortArray -> bundle.putShortArray(key, value as ShortArray)
            is String -> bundle.putString(key, value as String)
            is Serializable -> bundle.putSerializable(key, value as Serializable)
            else -> throw IllegalStateException("Type of $key is not supported.")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun restoreStateFrom(bundle: Bundle) {
        val restoredValue: PropertyType = when (value) {
            is Boolean -> bundle.getBoolean(key, defaultValue as Boolean) as PropertyType
            is BooleanArray -> bundle.getBooleanArray(key) as PropertyType
            is Bundle -> bundle.getBundle(key) as PropertyType
            is Byte -> bundle.getByte(key, defaultValue as Byte) as PropertyType
            is ByteArray -> bundle.getByteArray(key) as PropertyType
            is Char -> bundle.getChar(key, defaultValue as Char) as PropertyType
            is CharArray -> bundle.getCharArray(key) as PropertyType
            is Double -> bundle.getDouble(key, defaultValue as Double) as PropertyType
            is DoubleArray -> bundle.getDoubleArray(key) as PropertyType
            is Float -> bundle.getFloat(key, defaultValue as Float) as PropertyType
            is FloatArray -> bundle.getFloatArray(key) as PropertyType
            is Int -> bundle.getInt(key, defaultValue as Int) as PropertyType
            is IntArray -> bundle.getIntArray(key) as PropertyType
            is Long -> bundle.getLong(key, defaultValue as Long) as PropertyType
            is LongArray -> bundle.getLongArray(key) as PropertyType
            is Parcelable -> bundle.getParcelable<Parcelable>(key) as PropertyType
            is Short -> bundle.getShort(key, defaultValue as Short) as PropertyType
            is ShortArray -> bundle.getShortArray(key) as PropertyType
            is String -> bundle.getString(key, defaultValue as String) as PropertyType
            is Serializable -> bundle.getSerializable(key) as PropertyType
            else -> throw IllegalStateException("Type of $key is not supported.")
        }
        value = restoredValue ?: defaultValue
    }
}