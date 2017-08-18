package com.hjonas.data.services.carattributes.model

import com.google.gson.annotations.SerializedName

data class CarAttributes(val regno: String
                         , val vin: String
                         , val brand: String
                         , val year: Int
                         , @SerializedName("gearbox_type") val gearboxType: String
                         , @SerializedName("fuel_types") val fuelTypes: List<String>
                         , val emission: Emission?
                         , val fuel: Fuel?)

// Data classes for emissions information
data class Emission(val gasoline: FuelEmission?
                    , val diesel: FuelEmission?) {
    data class FuelEmission(val co2: DrivingValues)
}

// Data classes for fuel information
data class Fuel(val gasoline: FuelType?
                , val diesel: FuelType?) {
    data class FuelType(@SerializedName("average_consumption") val averageConsumption: DrivingValues)
}

data class DrivingValues(val urban: Double?
                         , val mixed: Double?
                         , val rural: Double?)

object Brands {
    const val Volvo = "volvo"
    const val Ford = "ford"
    const val Saab = "saab"
    const val Rover = "rover"
}

object GearBoxType {
    const val Manual = "manual"
    const val Automatic = "automatic"
}

object FuelTypes {
    const val Gasoline = "gasoline"
    const val Diesel = "diesel"
}

fun DrivingValues.hasValues() = (urban != null || mixed != null || rural != null)