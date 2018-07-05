package com.hjonas.carattr.ui.attributeslist.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.attributeslist.*
import com.hjonas.carattr.utils.getDrawableCompat
import com.hjonas.carattr.utils.setVisible
import kotlinx.android.synthetic.main.info_item_driving_values.view.*


abstract class DrivingValuesViewHolder(context: Context, parent: ViewGroup) :
    ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.info_item_driving_values, parent, false)) {

  private val titleTv = itemView.sectionTitleTv
  protected val drivingConditionMixedLabel: TextView = itemView.drivingConditionMixedLabel
  protected val drivingConditionMixedValue: TextView = itemView.drivingConditionMixedTv
  protected val drivingConditionRuralLabel: TextView = itemView.drivingConditionRuralLabel
  protected val drivingConditionRuralValue: TextView = itemView.drivingConditionRuralTv
  protected val drivingConditionUrbanLabel: TextView = itemView.drivingConditionUrbanLabel
  protected val drivingConditionUrbanValue: TextView = itemView.drivingConditionUrbanTv

  abstract fun getSectionTitle(data: CarAttributeItem): String
  abstract fun getSectionIcon(data: CarAttributeItem): Int
  abstract fun bindValues(data: CarAttributeItem)

  override fun bind(data: CarAttributeItem) {
    hideAllValues()
    titleTv.text = getSectionTitle(data)
    val icon = itemView.context.getDrawableCompat(getSectionIcon(data))
    titleTv.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)
    bindValues(data)
  }

  protected fun getNameForFuelType(fuelType: FuelType): String =
      when (fuelType) {
        is Diesel -> itemView.context.getString(R.string.fuel_type_diesel)
        is Gasoline -> itemView.context.getString(R.string.fuel_type_gasoline)
        is UndefinedType -> ""
      }

  private fun hideAllValues() {
    drivingConditionRuralLabel.setVisible(false)
    drivingConditionRuralValue.setVisible(false)
    drivingConditionMixedLabel.setVisible(false)
    drivingConditionMixedValue.setVisible(false)
    drivingConditionUrbanLabel.setVisible(false)
    drivingConditionUrbanValue.setVisible(false)
  }
}
