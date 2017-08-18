package com.hjonas.carattr.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.ui.presenter.CarAttributesPresenter
import com.hjonas.data.services.carattributes.model.CarAttributes
import com.hjonas.data.services.carattributes.model.Emission
import com.hjonas.data.services.carattributes.model.Fuel
import kotlinx.android.synthetic.main.activity_car_attributes.*
import kotlinx.android.synthetic.main.car_attributes_contents.*

class CarAttributesActivity : AppCompatActivity(), CarAttributesContract.View {

    companion object {
        private const val INTENT_EXTRA_VIN = "intent.extra.vin"

        /**
         * Creates and returns an [Intent] that can be used to start this activity with a vin-parameter for the car to show attributes for
         */
        fun newIntent(context: Context, vin: String) =
                Intent(context, CarAttributesActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_VIN, vin)
                }
    }

    lateinit var presenter: CarAttributesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_attributes)

        val vin = intent?.getStringExtra(INTENT_EXTRA_VIN)
        presenter = CarAttributesPresenter(this, vin)
        presenter.subscribe()
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
    }

    override fun showVehicleInformation(attributes: CarAttributes) {
        with(attributes) {
            carAttributeModelYearTv.text = "${brand.capitalize()} ($year)"
            carAttributeRegNbrTv.text = regno
            carAttributeGearboxTv.text = gearboxType.capitalize()
            carAttributeFuelTypeTv.text = fuelTypes.map { it.capitalize() }.joinToString(separator = ", ")
        }
    }

    override fun showFuelInformation(fuel: Fuel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmissionsInformation(emission: Emission) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showConnectionProblemError() {
        Toast.makeText(this, "Connection progrem", Toast.LENGTH_LONG).show()
    }

    override fun showIncorrectResponseError(code: Int) {
        Toast.makeText(this, "Error response: $code", Toast.LENGTH_LONG).show()
    }
}
