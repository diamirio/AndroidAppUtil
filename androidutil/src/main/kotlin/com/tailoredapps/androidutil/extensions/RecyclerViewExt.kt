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

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Smooth scrolls up a [RecyclerView] when it has not been scrolled past the firstVisiblePosition items.
 * Otherwise the scroll will happen instantly.
 */
fun <R : RecyclerView> R.smoothScrollUp(firstVisiblePosition: Int = 50) {
    val layoutManager = layoutManager ?: return
    val firstVisible = when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
        //todo implement for GridLayoutManager and StaggeredGridLayoutManager
        else -> Int.MAX_VALUE
    }

    if (firstVisible < firstVisiblePosition) smoothScrollToPosition(0)
    else scrollToPosition(0)
}

/**
 * Adds a ScrollListener that checks whether the item at the itemIndex has been scrolled past.
 * Possible use case: Display a "Scroll Up"-Button when scrolled past the item at itemIndex.
 */
fun <R : RecyclerView> R.addScrolledPastItemListener(
    itemIndex: Int = 0,
    scrolledPast: (Boolean) -> Unit
): RecyclerView.OnScrollListener {
    return object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = layoutManager ?: return
            when (layoutManager) {
                is LinearLayoutManager -> scrolledPast.invoke(layoutManager.findFirstVisibleItemPosition() > itemIndex)
                else -> {
                    //todo implement for GridLayoutManager and StaggeredGridLayoutManager
                }
            }
        }
    }.also(::addOnScrollListener)
}

/**
 * Checks whether the [RecyclerView] has scrolled past a certain item at size - threshold.
 * Use this for paginated loading.
 */
fun <R : RecyclerView> R.shouldLoadMore(threshold: Int = 8): Boolean {
    val layoutManager = layoutManager ?: return false
    return when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition() + threshold > layoutManager.itemCount
        //todo implement for GridLayoutManager and StaggeredGridLayoutManager
        else -> false
    }
}