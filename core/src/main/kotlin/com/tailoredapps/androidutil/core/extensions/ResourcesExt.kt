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

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan


/**
 * Creates a [CharSequence] of bulleted points from an [Iterable].
 */
fun <R : Resources, T : Any> R.createBulletCharSequence(
    lines: Iterable<T>,
    adapter: ((T) -> CharSequence)? = null
): CharSequence = SpannableStringBuilder().also { ssb ->
    val gapWidth = Math.round(8f * displayMetrics.density)
    lines.forEachIndexed { index, line ->
        val text = "${(adapter?.invoke(line) ?: line.toString())}${if (index == lines.count() - 1) "" else "\n"}"
        ssb.append(text, BulletSpan(gapWidth), Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}