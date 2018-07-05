package com.hjonas.carattr.ui.attributeslist.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.attributeslist.CarAttributeItem
import com.hjonas.carattr.utils.getDrawableCompat
import kotlinx.android.synthetic.main.info_item_car.view.*

class CarInfoViewHolder(context: Context, parent: ViewGroup) :
    ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.info_item_car, parent, false)) {

  private val headerTv = itemView.carDetailsHeaderTv
  private val modelYearTv = itemView.carAttributeModelYearTv
  private val regNbrTv = itemView.carAttributeRegNbrTv
  private val gearBoxTv = itemView.carAttributeGearboxTv
  private val fuelTypeTv = itemView.carAttributeFuelTypeTv

  override fun bind(data: CarAttributeItem) {
    val icon = itemView.context.getDrawableCompat(R.drawable.ic_car)
    headerTv.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)

    with(data.attributes) {
      modelYearTv.text = itemView.context.getString(R.string.car_info_model_year, brand.capitalize(), year.toString())
      regNbrTv.text = regno
      gearBoxTv.text = gearboxType.capitalize()
      fuelTypeTv.text = fuelTypes.joinToString(separator = ", ") { it.capitalize() }
    }
  }
}
