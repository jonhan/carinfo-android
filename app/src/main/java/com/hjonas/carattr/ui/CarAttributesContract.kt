package com.hjonas.carattr.ui

import com.hjonas.data.services.carattributes.model.CarAttributes
import com.hjonas.data.services.carattributes.model.Emission
import com.hjonas.data.services.carattributes.model.Fuel

interface CarAttributesContract {

    companion object {
        const val CODE_UNKNOWN_ERROR = -1
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showConnectionProblemError()
        fun showIncorrectResponseError(code: Int)
        fun showVehicleInformation(attributes: CarAttributes)
        fun showFuelInformation(fuel: Fuel)
        fun showEmissionsInformation(emission: Emission)
    }

    interface Presenter {
        fun subscribe()
        fun unsubscribe()
        fun fetchData()
    }
}