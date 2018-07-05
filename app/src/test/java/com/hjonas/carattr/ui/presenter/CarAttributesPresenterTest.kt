package com.hjonas.carattr.ui.presenter

import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.ui.attributeslist.*
import com.hjonas.data.services.carattributes.CarAttributesService
import com.hjonas.data.services.carattributes.model.CarAttributes
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import org.junit.Before

class CarAttributesPresenterTest {

  private val TEST_CAR_ATTRIBUTES by lazy {
    CarAttributes("ABC123",
                  "VIN",
                  "Volvo",
                  2017,
                  "Manual",
                  listOf("Gasoline", "Diesel"),
                  null, null)
  }
  private val TEST_SECTIONS by lazy {
    listOf(CarAttributeItem(AttributeSectionType.FUEL_CONSUMPTION, TEST_CAR_ATTRIBUTES, Gasoline()),
           CarAttributeItem(AttributeSectionType.EMISSIONS, TEST_CAR_ATTRIBUTES, Diesel()))
  }

  private lateinit var presenter: CarAttributesPresenter
  private lateinit var view: CarAttributesContract.View
  private lateinit var sectionsProvider: AttributeSectionsProvider
  private lateinit var api: CarAttributesService

  @Before
  private fun setUp() {
    view = mock()
    sectionsProvider = mock { on { getSections(any()) } doReturn TEST_SECTIONS }
    api = mock { on { fetchCarAttributes() } doReturn Observable.just(TEST_CAR_ATTRIBUTES) }

    presenter = CarAttributesPresenter(view, "vin", sectionsProvider, api)
  }
}
