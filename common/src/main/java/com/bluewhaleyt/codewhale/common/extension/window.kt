package com.bluewhaleyt.codewhale.common.extension

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

val View.window: Window?
    get() {
        var current = context
        while (current is ContextWrapper) {
            if (current is Activity) {
                return current.window
            }
            current = current.baseContext
        }
        return null
    }

val View.isImeVisible: Boolean
    get() = ViewCompat.getRootWindowInsets(this)
        ?.isVisible(WindowInsetsCompat.Type.ime()) ?: false