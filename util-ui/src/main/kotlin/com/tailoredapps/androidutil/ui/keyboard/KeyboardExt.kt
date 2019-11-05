package com.tailoredapps.androidutil.ui.keyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable

/**
 * Created by alexandergrafl on 2019-11-05.
 */

/**
 * Creates an instance of [KeyboardStateManager] and returns the [KeyboardStatus] Observable
 */
val <A : AppCompatActivity> A.keyboardStates: Observable<KeyboardStatus>
        get() = KeyboardStateManager(this).status

/**
 * Creates an instance of [KeyboardStateManager] and returns the current [KeyboardStatus]
 */
val <A : AppCompatActivity> A.currentKeyboardStatus: KeyboardStatus
    get() = KeyboardStateManager(this).currentStatus

/**
 * Creates an instance of [KeyboardStateManager] and returns whether the current [KeyboardStatus] is [KeyboardStatus.OPEN]
 */
val <A : AppCompatActivity> A.isKeyboardOpen: Boolean
    get() = KeyboardStateManager(this).isKeyboardOpen

/**
 * Shows the keyboard for a view and focuses it.
 */
fun <V : View> V.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Hides the keyboard for a view and unfocuses it.
 */
fun <V : View> V.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isActive) inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    clearFocus()
}