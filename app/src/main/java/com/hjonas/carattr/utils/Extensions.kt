package com.hjonas.carattr.utils

import android.util.Log

/** Logs a debuggable-message where the tag will be the name of the class. */
fun Any.logMessage(message: String) {
    Log.d(this.javaClass.simpleName, message)
}

/** Logs an error-message where the tag will be the name of the class. */
fun Any.logError(message: String) {
    Log.e(this.javaClass.simpleName, message)
}