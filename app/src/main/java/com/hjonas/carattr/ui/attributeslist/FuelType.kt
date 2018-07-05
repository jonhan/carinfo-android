package com.hjonas.carattr.ui.attributeslist

sealed class FuelType
class Gasoline : FuelType()
class Diesel : FuelType()
class UndefinedType : FuelType()
