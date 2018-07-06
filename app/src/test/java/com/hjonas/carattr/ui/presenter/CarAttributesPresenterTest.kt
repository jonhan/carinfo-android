package com.hjonas.carattr.ui.presenter

import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.ui.attributeslist.*
import com.hjonas.data.services.carattributes.CarAttributesService
import com.hjonas.data.services.carattributes.model.CarAttributes
import com.hjonas.data.services.carattributes.model.DrivingValues
import com.hjonas.data.services.carattributes.model.Emission
import com.hjonas.data.services.carattributes.model.Fuel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class CarAttributesPresenterTest {

  private val TEST_CAR_ATTRIBUTES by lazy {
    val emission = Emission(gasoline = null, diesel = Emission.FuelEmission(DrivingValues(urban = 12.3, mixed = 45.6, rural = 78.9)))
    val fuel = Fuel(gasoline = Fuel.FuelType(DrivingValues(urban = 12.3, mixed = 45.6, rural = 78.9)), diesel = null)
    CarAttributes("ABC123",
                  "VIN",
                  "Volvo",
                  2017,
                  "Manual",
                  listOf("Gasoline", "Diesel"),
                  emission, fuel)
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
  fun setUp() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    view = mock()
    sectionsProvider = mock { on { getSections(any()) } doReturn TEST_SECTIONS }
    api = mock { on { fetchCarAttributes() } doReturn Observable.just(TEST_CAR_ATTRIBUTES) }

    presenter = CarAttributesPresenter(view, "vin", sectionsProvider, api)
  }

  @Test
  fun testFetchAttributesSuccessful() {
    whenever(api.fetchCarAttributes()).thenReturn(Observable.just(TEST_CAR_ATTRIBUTES))

    presenter.subscribe()

    verify(view).showLoading()
    verify(view).hideLoading()
    verify(sectionsProvider).getSections(eq(TEST_CAR_ATTRIBUTES))
    verify(view).showAttributeSections(eq(TEST_SECTIONS))
  }

  @Test
  fun testFetchAttributesFailed() {
    val exception: HttpException = mock {
      on { code() } doReturn 401
    }
    whenever(api.fetchCarAttributes()).thenReturn(Observable.error(exception))

    presenter.subscribe()

    verify(view).showLoading()
    verify(view).hideLoading()
    verify(view).showIncorrectResponseError(401)
  }
}
