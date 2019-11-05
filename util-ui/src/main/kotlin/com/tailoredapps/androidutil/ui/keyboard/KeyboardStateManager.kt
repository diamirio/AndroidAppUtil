package com.tailoredapps.androidutil.ui.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by alexandergrafl on 2019-11-05.
 * Manages access to the Android soft keyboard.
 * Credit to https://medium.com/@munnsthoughts/detecting-if-the-android-keyboard-is-open-using-kotlin-rxjava-2-8aee9fae262c
 * Use with care: This class keeps a reference to the view/activity!
 */
class KeyboardStateManager(private val activity: AppCompatActivity) : LifecycleObserver {

    private val stateSubject = BehaviorSubject.create<KeyboardStatus>()

    private val stateDisposable: Disposable

    init {
        activity.lifecycle.addObserver(this)
        /**
         * Observable of the status of the keyboard. Subscribing to this creates a
         * Global Layout Listener which is automatically removed when this
         * observable is disposed.
         */
        stateDisposable = Observable.create<KeyboardStatus> { emitter ->
            val rootView = activity.findViewById<View>(android.R.id.content)

            // why are we using a global layout listener? Surely Android
            // has callback for when the keyboard is open or closed? Surely
            // Android at least lets you query the status of the keyboard?
            // Nope! https://stackoverflow.com/questions/4745988/
            val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {

                val rect = Rect().apply { rootView.getWindowVisibleDisplayFrame(this) }

                val screenHeight = rootView.height

                // rect.bottom is the position above soft keypad or device button.
                // if keypad is shown, the rect.bottom is smaller than that before.
                val keypadHeight = screenHeight - rect.bottom

                // 0.15 ratio is perhaps enough to determine keypad height.
                if (keypadHeight > screenHeight * 0.15) {
                    emitter.onNext(KeyboardStatus.OPEN)
                } else {
                    emitter.onNext(KeyboardStatus.CLOSED)
                }
            }

            rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

            emitter.setCancellable {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            }
        }.distinctUntilChanged()
            .subscribe(stateSubject::onNext)
    }

    val status: Observable<KeyboardStatus>
        get() = stateSubject.hide()

    val currentStatus: KeyboardStatus
        get() = stateSubject.value ?: KeyboardStatus.UNKNOWN

    val isKeyboardOpen: Boolean
        get() = currentStatus == KeyboardStatus.OPEN

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        stateSubject.onComplete()
        stateDisposable.dispose()
        activity.lifecycle.removeObserver(this)
    }
}

enum class KeyboardStatus {
    OPEN, CLOSED, UNKNOWN
}