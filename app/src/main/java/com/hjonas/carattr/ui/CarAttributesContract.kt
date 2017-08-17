package com.hjonas.carattr.ui

import com.hjonas.data.services.carattributes.model.CarAttributes
import java.util.jar.Attributes

interface CarAttributesContract {

    companion object {
        const val CODE_UNKNOWN_ERROR = -1
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun connectionProblemError()
        fun incorrectResponseError(code: Int)
        fun showVehicleInformation(attributes: Attributes)
        fun showFuelInformation(fuel: CarAttributes.Fuel)
        fun showEmissionsInformation(emission: CarAttributes.Emission)
    }

    interface Presenter {
        fun subscribe()
        fun unsubscribe()
    }
}