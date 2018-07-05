package com.hjonas.carattr.ui

import com.hjonas.carattr.ui.attributeslist.CarAttributeItem

interface CarAttributesContract {

  companion object {
    const val CODE_UNKNOWN_ERROR = -1
  }

  interface View {
    fun showLoading()
    fun hideLoading()
    fun showConnectionProblemError()
    fun showIncorrectResponseError(code: Int)
    fun showAttributeSections(sections: List<CarAttributeItem>)
  }

  interface Presenter {
    fun subscribe()
    fun unsubscribe()
    fun fetchData()
  }
}
