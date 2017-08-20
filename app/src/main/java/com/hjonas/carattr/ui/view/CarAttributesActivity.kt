package com.hjonas.carattr.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.ui.presenter.CarAttributesPresenter
import com.hjonas.carattr.utils.setVisible
import com.hjonas.data.services.carattributes.model.CarAttributes
import com.hjonas.data.services.carattributes.model.DrivingValues
import com.hjonas.data.services.carattributes.model.Emission
import com.hjonas.data.services.carattributes.model.Fuel
import kotlinx.android.synthetic.main.activity_car_attributes.*
import kotlinx.android.synthetic.main.attribute_consumption_section.view.*
import kotlinx.android.synthetic.main.car_attributes_contents.*
import kotlinx.android.synthetic.main.error_info.*

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

    private lateinit var presenter: CarAttributesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_attributes)
        val vin = intent?.getStringExtra(INTENT_EXTRA_VIN)
        presenter = CarAttributesPresenter(this, vin)
        configListeners()
        presenter.subscribe()
    }

    private fun configListeners() {
        errorInfoRetryButton.setOnClickListener {
            errorInfoLayout.setVisible(false)
            presenter.fetchData()
        }
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
        contentsLayout.setVisible(true)
        with(attributes) {
            carAttributeModelYearTv.text = "${brand.capitalize()} ($year)"
            carAttributeRegNbrTv.text = regno
            carAttributeGearboxTv.text = gearboxType.capitalize()
            carAttributeFuelTypeTv.text = fuelTypes.map { it.capitalize() }.joinToString(separator = ", ")
        }
    }

    override fun showFuelInformation(fuel: Fuel) {
        fuel.gasoline?.let {
            val title = getString(R.string.section_title_fuel, getString(R.string.fuel_type_gasoline))
            addDrivingValuesSectionView(title, it.averageConsumption, formatFuelConsumptionValue)
        }
        fuel.diesel?.let {
            val title = getString(R.string.section_title_fuel, getString(R.string.fuel_type_diesel))
            addDrivingValuesSectionView(title, it.averageConsumption, formatFuelConsumptionValue)
        }
    }

    override fun showEmissionsInformation(emission: Emission) {
        emission.gasoline?.let {
            val title = getString(R.string.section_title_emissions, getString(R.string.fuel_type_gasoline))
            addDrivingValuesSectionView(title, it.co2, formatEmissionsValue)
        }
        emission.diesel?.let {
            val title = getString(R.string.section_title_emissions, getString(R.string.fuel_type_diesel))
            addDrivingValuesSectionView(title, it.co2, formatEmissionsValue)
        }
    }

    private fun addDrivingValuesSectionView(sectionTitle: String, drivingValues: DrivingValues, formatDrivingValue: (Double) -> String) {
        val sectionView = LayoutInflater.from(this).inflate(R.layout.attribute_consumption_section, carAttributesContainerLayout, false)
        sectionView.sectionTitleTv.text = sectionTitle
        sectionView.apply {
            drivingValues.mixed?.let {
                drivingConditionMixedLabel.setVisible(true)
                drivingConditionMixedTv.setVisible(true)
                drivingConditionMixedTv.text = formatDrivingValue(it)
            }
            drivingValues.rural?.let {
                drivingConditionRuralLabel.setVisible(true)
                drivingConditionRuralTv.setVisible(true)
                drivingConditionRuralTv.text = formatDrivingValue(it)
            }
            drivingValues.urban?.let {
                drivingConditionUrbanLabel.setVisible(true)
                drivingConditionUrbanTv.setVisible(true)
                drivingConditionUrbanTv.text = formatDrivingValue(it)
            }
        }
        carAttributesContainerLayout.addView(sectionView)
    }

    override fun showConnectionProblemError() {
        contentsLayout.setVisible(false)
        errorInfoLayout.setVisible(true)
        errorInfoIcon.setImageResource(R.drawable.ic_cloud_off)
        errorInfoMessageTv.text = getString(R.string.error_connection_problem)
    }

    override fun showIncorrectResponseError(code: Int) {
        contentsLayout.setVisible(false)
        errorInfoLayout.setVisible(true)
        errorInfoIcon.setImageResource(R.drawable.ic_error)
        errorInfoMessageTv.text = if (code == CarAttributesContract.CODE_UNKNOWN_ERROR) {
            getString(R.string.error_unknown)
        } else {
            getString(R.string.error_incorrect_response, code)
        }
    }

    private val formatFuelConsumptionValue: (value: Double) -> String = {
        val scaledValue: Double = it * 1000 * 100  // Scale up to liter/100km
        getString(R.string.consumption_liter_per_100_km, scaledValue)
    }

    private val formatEmissionsValue: (value: Double) -> String = {
        val scaledValue: Double = it * 1000 * 1000 // Scale up to gram/km
        getString(R.string.emissions_gram_per_km, scaledValue)
    }
}
