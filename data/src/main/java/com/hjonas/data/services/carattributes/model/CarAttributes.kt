package com.hjonas.data.services.carattributes.model

import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class CarAttributes(val regno: String
                         , val vin: String
                         , val brand: String
                         , val year: Int
                         , @SerializedName("gearbox_type") val gearboxType: String
                         , @SerializedName("fuel_types") val fuelTypes: List<String>
                         , val emission: Emission?
                         , val fuel: Fuel?) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelCarAttributes.CREATOR
    }
}

// Data classes for emissions information
@PaperParcel
data class Emission(val gasoline: FuelEmission?
                    , val diesel: FuelEmission?) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelEmission.CREATOR
    }

    @PaperParcel
    data class FuelEmission(val co2: DrivingValues) : PaperParcelable {
        companion object {
            @JvmField val CREATOR = PaperParcelEmission_FuelEmission.CREATOR
        }
    }
}

// Data classes for fuel information
@PaperParcel
data class Fuel(val gasoline: FuelType?
                , val diesel: FuelType?) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelFuel.CREATOR
    }

    @PaperParcel
    data class FuelType(@SerializedName("average_consumption") val averageConsumption: DrivingValues) : PaperParcelable {
        companion object {
            @JvmField val CREATOR = PaperParcelFuel_FuelType.CREATOR
        }
    }
}

@PaperParcel
data class DrivingValues(val urban: Double?
                         , val mixed: Double?
                         , val rural: Double?) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelDrivingValues.CREATOR
    }
}

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