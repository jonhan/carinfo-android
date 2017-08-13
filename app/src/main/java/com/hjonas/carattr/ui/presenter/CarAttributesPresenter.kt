package com.hjonas.carattr.ui.presenter

import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.utils.logMessage
import com.hjonas.data.ApiManager
import com.hjonas.data.services.carattributes.model.CarAttributes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class CarAttributesPresenter(val view: CarAttributesContract.View) : CarAttributesContract.Presenter {

    private var disposable: Disposable? = null

    override fun subscribe() {
        disposable = ApiManager.carInfoService().fetchCarAttributes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ handleAttributesFetched(it) }, { handleFetchAttributesFail(it) })
    }

    override fun unsubscribe() {
        disposable?.dispose()
    }

    private fun handleAttributesFetched(attributes: CarAttributes) {
        logMessage("handleAtributesFetched: $attributes")
    }

    private fun handleFetchAttributesFail(throwable: Throwable) {
        when (throwable) {
            is IOException -> view.connectionProblemError()
            is HttpException -> view.incorrectResponseError(throwable.code())
            else -> view.incorrectResponseError(CarAttributesContract.CODE_UNKNOWN_ERROR)
        }
    }
}