package com.hjonas.carattr.ui.presenter

import android.os.Bundle
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

    companion object {
        private const val BUNDLE_EXTRA_DATA = "extra.data"
    }

    private var disposable: Disposable? = null
    private var attributesData: CarAttributes? = null

    override fun subscribe() {
        if (attributesData == null) {
            fetchData()
        } else {
            attributesData?.let { handleAttributesFetched(it) }
        }
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
                .subscribe({
                    attributesData = it
                    handleAttributesFetched(it)
                }, { handleFetchAttributesFail(it) })
    }

    private fun handleAttributesFetched(attributes: CarAttributes) {
        view.showVehicleInformation(attributes)
        showFuelAndEmissionIfAvailable(attributes)
    }

    private fun showFuelAndEmissionIfAvailable(attributes: CarAttributes) {
        attributes.fuel?.let { view.showFuelInformation(it) }
        attributes.emission?.let { view.showEmissionsInformation(it) }
    }

    private fun handleFetchAttributesFail(throwable: Throwable) {
        logError("handleFetchAttributesFail: $throwable")
        when (throwable) {
            is IOException -> view.showConnectionProblemError()
            is HttpException -> view.showIncorrectResponseError(throwable.code())
            else -> view.showIncorrectResponseError(CarAttributesContract.CODE_UNKNOWN_ERROR)
        }
    }

    override fun restoreInstanceState(bundle: Bundle) {
        attributesData = bundle.getParcelable(BUNDLE_EXTRA_DATA)
    }

    override fun saveInstanceState(bundle: Bundle) {
        attributesData?.let {
            bundle.putParcelable(BUNDLE_EXTRA_DATA, it)
        }
    }
}