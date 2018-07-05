package com.hjonas.carattr.ui.attributeslist.viewholder

import android.view.ViewGroup
import com.hjonas.carattr.ui.attributeslist.AttributeSectionType


class ViewHolderFactory {

  fun createViewHolder(type: AttributeSectionType, parent: ViewGroup) =
      when (type) {
        AttributeSectionType.CAR_INFORMATION -> CarInfoViewHolder(parent.context, parent)
        AttributeSectionType.FUEL_CONSUMPTION -> FuelConsumptionViewHolder(parent.context, parent)
        AttributeSectionType.EMISSIONS -> EmissionsViewHolder(parent.context, parent)
      }
}
