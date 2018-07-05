package com.hjonas.carattr.ui.attributeslist.viewholder

import android.content.Context
import android.view.ViewGroup
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.attributeslist.CarAttributeItem
import com.hjonas.carattr.ui.attributeslist.Diesel
import com.hjonas.carattr.ui.attributeslist.Gasoline
import com.hjonas.carattr.utils.setVisible
import com.hjonas.data.services.carattributes.model.DrivingValues

class FuelConsumptionViewHolder(context: Context, parent: ViewGroup) :
    DrivingValuesViewHolder(context, parent) {

  override fun getSectionTitle(data: CarAttributeItem): String =
      itemView.context.getString(R.string.section_title_fuel, getNameForFuelType(data.fuelType))

  override fun getSectionIcon(data: CarAttributeItem): Int = R.drawable.ic_fuel

  override fun bindValues(data: CarAttributeItem) {
    when (data.fuelType) {
      is Diesel -> bindDrivingValues(data.attributes.fuel?.diesel?.averageConsumption)
      is Gasoline -> bindDrivingValues(data.attributes.fuel?.gasoline?.averageConsumption)
    }
  }

  private fun bindDrivingValues(drivingValues: DrivingValues?) {
    drivingValues ?: return
    drivingValues.mixed?.let {
      drivingConditionMixedLabel.setVisible(true)
      drivingConditionMixedValue.setVisible(true)
      drivingConditionMixedValue.text = formatFuelConsumption(it)
    }

    drivingValues.rural?.let {
      drivingConditionRuralLabel.setVisible(true)
      drivingConditionRuralValue.setVisible(true)
      drivingConditionRuralValue.text = formatFuelConsumption(it)
    }

    drivingValues.urban?.let {
      drivingConditionUrbanLabel.setVisible(true)
      drivingConditionUrbanValue.setVisible(true)
      drivingConditionUrbanValue.text = formatFuelConsumption(it)
    }
  }

  private fun formatFuelConsumption(value: Double): String {
    val scaledValue: Double = value * 1000 * 100  // Scale up to liter/100km
    return itemView.context.getString(R.string.consumption_liter_per_100_km, scaledValue)
  }
}
