package com.hjonas.carattr.utils

import android.util.Log
import android.view.View

/** Logs a debuggable-message where the tag will be the name of the class. */
fun Any.logMessage(message: String) {
    Log.d(this.javaClass.simpleName, message)
}

/** Logs an error-message where the tag will be the name of the class. */
fun Any.logError(message: String) {
    Log.e(this.javaClass.simpleName, message)
}

/** Set visibility to View.VISIBLE or View.GONE */
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}