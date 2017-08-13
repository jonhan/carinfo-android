package com.hjonas.carattr.ui

interface CarAttributesContract {

    companion object {
        const val CODE_UNKNOWN_ERROR = -1
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun connectionProblemError()
        fun incorrectResponseError(code: Int)
    }

    interface Presenter {
        fun subscribe()
        fun unsubscribe()
    }
}