package com.hjonas.carattr.ui.attributeslist

import com.hjonas.data.services.carattributes.model.CarAttributes


class AttributeSectionsProvider {
  fun getSections(carAttributes: CarAttributes): List<CarAttributeItem> {
    val items = mutableListOf<CarAttributeItem>()
    items.add(CarAttributeItem(AttributeSectionType.CAR_INFORMATION, carAttributes, null))
//    carAttributes.emission?.let {
//      it.diesel?.let { items.add(CarAttributeItem(AttributeSectionType.EMISSIONS, carAttributes, Diesel())) }
//      it.gasoline?.let { items.add(CarAttributeItem(AttributeSectionType.EMISSIONS, carAttributes, Gasoline())) }
//    }
//    carAttributes.fuel?.let {
//      it.diesel?.let { items.add(CarAttributeItem(AttributeSectionType.FUEL_CONSUMPTION, carAttributes, Diesel())) }
//      it.gasoline?.let { items.add(CarAttributeItem(AttributeSectionType.FUEL_CONSUMPTION, carAttributes, Gasoline())) }
//    }
    return items
  }
}
