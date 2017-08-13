package com.hjonas.carattr.ui

interface CarAttributesContract {
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