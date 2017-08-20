package com.hjonas.carattr.ui.presenter

import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.utils.logError
import com.hjonas.data.ApiManager
import com.hjonas.data.services.carattributes.model.CarAttributes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class CarAttributesPresenter(val view: CarAttributesContract.View, val vin: String? = null) : CarAttributesContract.Presenter {

    private var disposable: Disposable? = null

    override fun subscribe() {
        fetchData()
    }

    override fun unsubscribe() {
        disposable?.dispose()
    }

    override fun fetchData() {
        //TODO: Use the vin-field to fetch attributes for a specific car once an API supporting this is available.
        disposable = ApiManager.carAttributesService.fetchCarAttributes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doFinally { view.hideLoading() }
                .subscribe({ handleAttributesFetched(it) }, { handleFetchAttributesFail(it) })
    }


    private fun handleAttributesFetched(attributes: CarAttributes) {
        view.showVehicleInformation(attributes)
        showFuelAndEmissionIfAvailable(attributes)
    }

    private fun showFuelAndEmissionIfAvailable(attributes: CarAttributes) {
        with(attributes) {
            attributes.fuel?.let { view.showFuelInformation(it) }
            attributes.emission?.let { view.showEmissionsInformation(it) }
        }
    }

    private fun handleFetchAttributesFail(throwable: Throwable) {
        logError("handleFetchAttributesFail: $throwable")
        when (throwable) {
            is IOException -> view.showConnectionProblemError()
            is HttpException -> view.showIncorrectResponseError(throwable.code())
            else -> view.showIncorrectResponseError(CarAttributesContract.CODE_UNKNOWN_ERROR)
        }
    }
}