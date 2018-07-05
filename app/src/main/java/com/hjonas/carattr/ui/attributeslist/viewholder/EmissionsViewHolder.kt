package com.hjonas.carattr.ui.attributeslist.viewholder

import android.content.Context
import android.view.ViewGroup
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.attributeslist.CarAttributeItem
import com.hjonas.carattr.ui.attributeslist.Diesel
import com.hjonas.carattr.ui.attributeslist.Gasoline
import com.hjonas.carattr.utils.setVisible
import com.hjonas.data.services.carattributes.model.DrivingValues

class EmissionsViewHolder(context: Context, parent: ViewGroup)
  : DrivingValuesViewHolder(context, parent) {

  override fun getSectionTitle(data: CarAttributeItem): String =
      itemView.context.getString(R.string.section_title_emissions, getNameForFuelType(data.fuelType))

  override fun getSectionIcon(data: CarAttributeItem): Int = R.drawable.ic_emission_cloud

  override fun bindValues(data: CarAttributeItem) {
    when (data.fuelType) {
      is Diesel -> bindDrivingValues(data.attributes.emission?.diesel?.co2)
      is Gasoline -> bindDrivingValues(data.attributes.emission?.gasoline?.co2)
    }
  }

  private fun bindDrivingValues(drivingValues: DrivingValues?) {
    drivingValues ?: return
    drivingValues.mixed?.let {
      drivingConditionMixedLabel.setVisible(true)
      drivingConditionMixedValue.setVisible(true)
      drivingConditionMixedValue.text = formatEmissions(it)
    }

    drivingValues.rural?.let {
      drivingConditionRuralLabel.setVisible(true)
      drivingConditionRuralValue.setVisible(true)
      drivingConditionRuralValue.text = formatEmissions(it)
    }

    drivingValues.urban?.let {
      drivingConditionUrbanLabel.setVisible(true)
      drivingConditionUrbanValue.setVisible(true)
      drivingConditionUrbanValue.text = formatEmissions(it)
    }
  }

  private fun formatEmissions(value: Double): String {
    val scaledValue: Double = value * 1000 * 1000 // Scale up to gram/km
    return itemView.context.getString(R.string.emissions_gram_per_km, scaledValue)
  }
}
