package com.hjonas.data.services.carattributes

import com.hjonas.data.services.carattributes.model.CarAttributes
import io.reactivex.Observable
import retrofit2.http.GET

interface CarAttributesService {

    @GET("vehicle-attributes.json")
    fun fetchCarAttributes(): Observable<CarAttributes>
}