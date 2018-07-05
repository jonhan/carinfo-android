package com.hjonas.carattr.ui.attributeslist

import com.hjonas.data.services.carattributes.model.CarAttributes

/**
 * Future development, define a base sealed class and then data classes inheriting this
 * which only contains data relevant for that section.
 */
data class CarInfoItem(val type: AttributeSectionType, val data: CarAttributes)
